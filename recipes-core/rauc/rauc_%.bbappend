FILESEXTRAPATHS_prepend := "${THISDIR}/rauc:"

DEPENDS_append = " dbus"

RAUC_DEMO_KEYRING_FILE = "demo-ca.cert.pem"

do_install[postfuncs] += "do_set_compatible"

python do_set_compatible () {
    import configparser

    machine = d.getVar("MACHINE", expand=True)
    d_dir = d.getVar("D", expand=True)
    conf_file = d_dir + "/etc/rauc/system.conf"
    parser = configparser.ConfigParser()
    parser.read(conf_file)
    parser['system']['compatible'] = machine
    with open(conf_file, 'w') as configfile:
        parser.write(configfile)
}


python __anonymous () {
    use_vault = bb.utils.contains('RAUC_USE_VAULT', '1', True, False, d)
    if use_vault:
        d.appendVarFlag('do_unpack', 'postfuncs', ' do_fetch_vault_keys')
    else:
        d.setVar('RAUC_KEYRING_FILE', d.getVar('RAUC_DEMO_KEYRING_FILE', expand=True))
    # print only once per build
    if d.getVar('BB_WORKERCONTEXT') != '1':
        if use_vault:
            bb.warn("RAUC will use vault keyring file!")
        else:
            bb.warn("RAUC will use _DEMO_ keyring file!")
}


def check_key_presence(key_dict, key):
    if key not in key_dict:
        bb.fatal("Key {} not found in vault data.".format(key))
        return False


def write_file(content, path):
    dir_path = os.path.dirname(path)
    if not os.path.isdir(dir_path):
        bb.fatal("Path \"{}\" does not exist!".format(dir_path))
        return False
    with open(path, 'w') as out_file:
        out_file.write(content)
    return True


python do_fetch_vault_keys () {
    import json
    import urllib.request

    vault_key_cert = ("cert", d.getVar('RAUC_CERT_FILE', expand=True))
    vault_key_ca_chain = ("ca_chain", d.expand('${WORKDIR}/${RAUC_KEYRING_FILE}'))
    vault_key_private_key = ("rsa_private_key", d.getVar('RAUC_KEY_FILE', expand=True))
    vault_key_customer = "customer"
    vault_key_data = "data"

    os.remove(vault_key_ca_chain[1])

    vault_url = d.getVar('RAUC_VAULT_URL', expand=True)
    vault_serial = d.getVar('RAUC_VAULT_SERIAL', expand=True)
    token = d.getVar('RAUC_VAULT_TOKEN', expand=True)

    if vault_url is None:
        bb.fatal("RAUC_VAULT_URL is not set!")
        return

    if vault_serial is None:
        bb.fatal("RAUC_VAULT_SERIAL is not set!")
        return

    if token is None:
        bb.fatal("RAUC_VAULT_TOKEN is not set!")
        return

    api_url = "{}{}".format(vault_url, vault_serial)
    headers = {'X-Vault-Token': token}

    req = urllib.request.Request(api_url, headers=headers)
    result = None
    try:
        with urllib.request.urlopen(req) as response:
            vault_response = json.loads(response.read().decode('utf-8'))

    except urllib.error.URLError as e:
        bb.fatal(e.reason)
        return
    except urllib.error.HTTPError as e:
        bb.fatal('The vault server couldn\'t fulfill the request.')
        bb.fatal('Error code: {}'.format(e.code))
        return

    if vault_key_data not in vault_response:
        bb.fatal("Vault response invalid")
        return

    data = vault_response[vault_key_data][vault_key_data]

    for key in (vault_key_cert[0], vault_key_ca_chain[0], vault_key_private_key[0], vault_key_customer):
        if check_key_presence(data, key) is False:
            return

    bb.warn('Using CA keyring file for customer: {}'.format(data[vault_key_customer]))

    (ca_chain, path) = vault_key_ca_chain
    if write_file(data[ca_chain], path) is False:
        return
}
