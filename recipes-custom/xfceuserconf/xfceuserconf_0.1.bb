SUMMARY = "XFCE files for the base system."
DESCRIPTION = "This package provides system configuration files and alike."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += " file://default.xml \
            file://launcher-9/15306022021.desktop \
            file://launcher-10/15306022022.desktop \
            file://launcher-18/15306135134.desktop \
            file://cpugraph-17.rc \
            file://xsettings.xml \
            file://xfce4-panel.xml \
            file://xfwm4.xml \
            file://thunar/thunar.xml \
            file://thunar/mimeapps-thunar.list \
            file://xfce4-desktop.xml \
            file://christlogo.png \
            file://README.txt \
            file://terminalrc \
           "

S = "${WORKDIR}/"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

DEPENDS = "libpam"
RDEPENDS_${PN} = "bash"


do_install () {

# Disable promt Use default config or One empty panel and use default config on xfce, and set positon of taskbar icons and taskbar
        install -m 0775 -d ${D}/home/root/.config/xfce4/panel
        install -m 0664 ${WORKDIR}/default.xml ${D}/home/root/.config/xfce4/panel/default.xml

# taskbar icons
        install -m 0775 -d ${D}/home/root/.config/xfce4/panel/launcher-9
        install -m 0664 ${WORKDIR}/launcher-9/15306022021.desktop ${D}/home/root/.config/xfce4/panel/launcher-9/15306022021.desktop
        install -m 0775 -d ${D}/home/root/.config/xfce4/panel/launcher-10
        install -m 0664 ${WORKDIR}/launcher-10/15306022022.desktop ${D}/home/root/.config/xfce4/panel/launcher-10/15306022022.desktop
        install -m 0775 -d ${D}/home/root/.config/xfce4/panel/launcher-18
        install -m 0664 ${WORKDIR}/launcher-18/15306135134.desktop ${D}/home/root/.config/xfce4/panel/launcher-18/15306135134.desktop
        install -m 0664 ${WORKDIR}/cpugraph-17.rc ${D}/home/root/.config/xfce4/panel/cpugraph-17.rc

# Set Destktop appearance from Xfce to Adwaita because of strange Menu bar of xfce4-terminal, taskbar
# xsettings.xml set theme to Adwaita, icon theme Adwaita
# xfce4-panel.xml set taskbar view
# xfwm4.xml set two workspaces in tasktbar not std 4
        install -m 0775 -d ${D}/home/root/.config/xfce4/xfconf/xfce-perchannel-xml
        install -m 0664 ${WORKDIR}/xsettings.xml ${D}/home/root/.config/xfce4/xfconf/xfce-perchannel-xml/xsettings.xml
        install -m 0664 ${WORKDIR}/xfce4-panel.xml ${D}/home/root/.config/xfce4/xfconf/xfce-perchannel-xml/xfce4-panel.xml
        install -m 0664 ${WORKDIR}/xfwm4.xml ${D}/home/root/.config/xfce4/xfconf/xfce-perchannel-xml/xfwm4.xml

# Set christ logo as desktop background for all users, hide desktop icon home, filesystem, trash, removable
	install -m 0664 ${WORKDIR}/xfce4-desktop.xml ${D}/home/root/.config/xfce4/xfconf/xfce-perchannel-xml/xfce4-desktop.xml
        install -m 0755 -d ${D}/${datadir}/backgrounds/xfce
        install -m 0644 ${WORKDIR}/christlogo.png ${D}/${datadir}/backgrounds/xfce/christlogo.png

# Copy README to Desktop
	install -m 0775 -d ${D}/home/root/Desktop
        install -m 0664 ${WORKDIR}/README.txt ${D}/home/root/Desktop/README.txt

# Show root@pixi-cdl100:/# in terminal, in xfce-terminal enable Run command as login shell
	install -m 0775 -d ${D}/home/root/.config/xfce4/terminal
	install -m 0664 ${WORKDIR}/terminalrc ${D}/home/root/.config/xfce4/terminal/terminalrc

# Run shell scpripts on thunar by default
        install -m 0664 ${WORKDIR}/thunar/thunar.xml ${D}/home/root/.config/xfce4/xfconf/xfce-perchannel-xml/thunar.xml

# Assignment which file is open with which application in thunar
        install -m 0775 -d ${D}/home/root/.config
        install -m 0664 ${WORKDIR}/thunar/mimeapps-thunar.list ${D}/home/root/.config/mimeapps.list
}


FILES_${PN} += "/home/root/.config/xfce4/panel/default.xml \
	        /home/root/.config/xfce4/panel/launcher-9/15306022021.desktop \
	        /home/root/.config/xfce4/panel/launcher-10/15306022022.desktop \
                /home/root/.config/xfce4/panel/launcher-18/15306135134.desktop \
                /home/root/.config/xfce4/panel/cpugraph-17.rc \
                /home/root/.config/xfce4/xfconf/xfce-perchannel-xml/xsettings.xml \
                /home/root/.config/xfce4/xfconf/xfce-perchannel-xml/xfce4-panel.xml \
                /home/root/.config/xfce4/xfconf/xfce-perchannel-xml/xfwm4.xml \
                /home/root/.config/xfce4/xfconf/xfce-perchannel-xml/thunar.xml \
                /home/root/.config/mimeapps.list \
	        /home/root/.config/xfce4/xfconf/xfce-perchannel-xml/xfce4-desktop.xml \
                ${datadir}/backgrounds/xfce/christlogo.png \
                /home/root/Desktop/README.txt \
		/home/root/.config/xfce4/terminal/terminalrc \
		"

DIRFILES = "1"

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
