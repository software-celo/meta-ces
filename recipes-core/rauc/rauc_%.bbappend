FILESEXTRAPATHS_prepend := "${THISDIR}/rauc:"

python do_prepare_comp () {

    #Avoid warnings: use 4 spaces instead of tabs"
    import configparser
    _MACHINE = d.getVar("MACHINE",True)
    _D = d.getVar("D",True)
    _CONF_FILE = "/etc/rauc/system.conf"
    parser = configparser.ConfigParser()
    parser.read(_D + _CONF_FILE)
    parser['system']['compatible'] = _MACHINE
    with open(_D + _CONF_FILE, 'w') as configfile:
        parser.write(configfile)
}

DEPENDS_append = " dbus"

do_install[postfuncs] += "do_prepare_comp"
