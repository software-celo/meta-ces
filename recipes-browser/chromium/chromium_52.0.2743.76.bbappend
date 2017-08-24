# Add a Systemd Service for Chromium in kiosk mode and add several
# necessary flags to run on the i.MX6.
# 
# !Note: Even more flags are included in the google-chrome-kiosk script.
#
# Copyright (C) 2017 Christ Electronic Systems GmbH
# Peter Fink <pfink@christ-es.de>

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " file://google-chrome-kiosk.desktop \
                   file://google-chrome-kiosk \
                   file://browser.conf \
                   file://chromium-autostart.service"

# Had to remove '--gpu-no-context-lost' in chromium 48 from extra args i.e. ignore-lost-context from
# PACKAGECONFIG because it caused problems in at least one case. meta-freescale activates it, so leave
# it this way for now.
# Use more HW acceleration flags from boundary devices: "--ignore-gpu-blacklist --enable-gpu-rasterization
# --enable-zero-copy --enable-native-gpu-memory-buffers"
# (https://github.com/boundarydevices/meta-boundary/commit/be923c0ff7567653782697f8c24ed43a6dc97f0c)

CHROMIUM_EXTRA_ARGS += " --num-raster-threads=2 --window-depth=24 --flag-switches-begin --ignore-gpu-blacklist --flag-switches-end --touch-events=enabled --gpu-sandbox-start-early --enable-gpu-rasterization --enable-zero-copy --enable-native-gpu-memory-buffers"

# Note: We use some more arguments than kiosk-mode PACKAGECONFIG does, but use it anyway
PACKAGECONFIG_append = " disable-api-keys-info-bar kiosk-mode"
# Remove proprietary codecs
PACKAGECONFIG_remove = "proprietary-codecs "

# Remove libav dependency, because internal ffmpeg is used
DEPENDS_remove = "libav "
RDEPENDS_${PN} += " xinit libexif"

do_install_append() {
	install -d ${D}${systemd_unitdir}/system
	install -d ${D}${sysconfdir}
	install -d ${D}${sysconfdir}/systemd/system/graphical.target.wants
	install -m 0644 ${WORKDIR}/chromium-autostart.service ${D}${systemd_unitdir}/system/chromium-autostart.service
	install -m 0644 ${WORKDIR}/browser.conf ${D}${sysconfdir}/browser.conf

	install -Dm 0644 ${WORKDIR}/google-chrome-kiosk.desktop ${D}${datadir}/applications/google-chrome-kiosk.desktop
	install -Dm 0755 ${WORKDIR}/google-chrome-kiosk ${D}${bindir}/google-chrome-kiosk

        sed -i "s/^CHROME_EXTRA_ARGS=\"\"/CHROME_EXTRA_ARGS=\"${CHROMIUM_EXTRA_ARGS}\"/" ${D}${bindir}/google-chrome-kiosk

	# Do activate autostart by default
	ln -sf ${systemd_unitdir}/system/chromium-autostart.service \
		${D}${sysconfdir}/systemd/system/graphical.target.wants/chromium-autostart.service
}

FILES_${PN} += "${systemd_unitdir}/system/*.service ${sysconfdir}/browser.conf ${sysconfdir}/systemd/system/graphical.target.wants/chromium-autostart.service ${datadir}/applications/google-chrome-kiosk.desktop ${bindir}/google-chrome-kiosk"
