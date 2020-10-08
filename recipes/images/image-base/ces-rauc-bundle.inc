SUMMARY = "CES RAUC bundle for rootfs, kernel, bootloader & devictree"

LICENSE = "MIT"

PV ?= "V1.0"

inherit bundle features_check

REQUIRED_DISTRO_FEATURES = "rauc"

RAUC_BUNDLE_COMPATIBLE = "${MACHINE}"
RAUC_BUNDLE_VERSION = "${PV}-${PR}"

RAUC_BUNDLE_SLOTS = "rootfs bootloader"

RAUC_SLOT_rootfs ?= "ces-console-image"
RAUC_SLOT_rootfs[type] = "image"

RAUC_SLOT_bootloader = "u-boot-fslc-ces"
RAUC_SLOT_bootloader[type] = "file"
RAUC_SLOT_bootloader[file] = "u-boot.rauc"

RAUC_KEY_FILE = "${WORKDIR}/certs/rauc.key.pem"
RAUC_CERT_FILE = "${WORKDIR}/certs/rauc.cert.pem"

RAUC_DEMO_KEY_FILE := "${THISDIR}/rauc-demo-certs/rauc-demo-1.key.pem"
RAUC_DEMO_CERT_FILE := "${THISDIR}/rauc-demo-certs/rauc-demo-1.cert.pem"

TIME := "${@time.strftime('%H%M%S',time.localtime())}"
DATE := "${@time.strftime('%Y%m%d',time.localtime())}"

BUNDLE_NAME ?= "${RAUC_SLOT_rootfs}-${MACHINE}-${DATETIME}-${PV}"

do_unpack[vardeps] += "DISTRO_FEATURES RAUC_VAULT_URL RAUC_VAULT_SERIAL RAUC_VAULT_TOKEN"

do_unpack_prepend() {
    import shutil
    import os

    bundle_path = d.getVar('BUNDLE_DIR', expand=True)
    if os.path.isdir(bundle_path):
        shutil.rmtree(bundle_path)
        bb.note("wiping bundle directory to avoid bundle contamination")
}


python __anonymous () {
    use_vault = bb.utils.contains('RAUC_USE_VAULT', '1', True, False, d)
    if use_vault:
        d.appendVarFlag('do_unpack', 'postfuncs', ' do_fetch_vault_keys')
    else:
        d.setVar('RAUC_KEY_FILE', d.getVar('RAUC_DEMO_KEY_FILE', expand=True))
        d.setVar('RAUC_CERT_FILE', d.getVar('RAUC_DEMO_CERT_FILE', expand=True))
    # print only once per build
    if d.getVar('BB_WORKERCONTEXT') != '1':
        if use_vault:
            bb.warn("RAUC bundle signing with vault activated.")
        else:
            bb.warn("RAUC will use _DEMO_ bundle signing key!")
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
    import shutil

    vault_key_cert = ("cert", d.getVar('RAUC_CERT_FILE', expand=True))
    vault_key_ca_chain = ("ca_chain", d.getVar('RAUC_KEYRING_FILE', expand=True))
    vault_key_private_key = ("rsa_private_key", d.getVar('RAUC_KEY_FILE', expand=True))
    vault_key_customer = "customer"
    vault_key_data = "data"

    key_dir = os.path.dirname(d.getVar('RAUC_KEY_FILE', expand=True))

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

    bb.warn('Using bundle signing key for customer: {}'.format(data[vault_key_customer]))

    if os.path.isdir(key_dir):
        shutil.rmtree(key_dir)
    os.mkdir(key_dir)

    for (key, path) in (vault_key_cert, vault_key_private_key):
        if write_file(data[key], path) is False:
            return
}
