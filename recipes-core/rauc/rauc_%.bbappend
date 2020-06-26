FILESEXTRAPATHS_prepend := "${THISDIR}/rauc:"

do_unpack_prepend() {
    import os
    import shutil

    bundle_path = d.expand("${BUNDLE_DIR}")
    if os.path.isdir(bundle_path):
        shutil.rmtree(bundle_path)
        bb.warn("wiping bundle directory to avoid bundle contamination")
}


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
