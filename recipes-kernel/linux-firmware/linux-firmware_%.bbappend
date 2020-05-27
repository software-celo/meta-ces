# Packages split does not support generic split for rtl_bt
# Therefore a package split for rtl8723 bluetooth firmware is added here

LICENSE_${PN}-rtl_bt8723 = "Firmware-rtlwifi_firmware & WHENCE"
LICENSE_${PN}-rtl_bt8821 = "Firmware-rtlwifi_firmware & WHENCE"
LICENSE_${PN}-rtl_bt8822 = "Firmware-rtlwifi_firmware & WHENCE"
LICENSE_${PN}-rtl_bt8192 = "Firmware-rtlwifi_firmware & WHENCE"
LICENSE_${PN}-rtl_bt8761 = "Firmware-rtlwifi_firmware & WHENCE"
LICENSE_${PN}-rtl_bt8812 = "Firmware-rtlwifi_firmware & WHENCE"

FILES_${PN}-rtl_bt8723 = " \
	${nonarch_base_libdir}/firmware/rtl_bt/rtl8723*.bin \
"

FILES_${PN}-rtl_bt8821 = " \
	${nonarch_base_libdir}/firmware/rtl_bt/rtl8821*.bin \
"

FILES_${PN}-rtl_bt8822 = " \
	${nonarch_base_libdir}/firmware/rtl_bt/rtl8822*.bin \
"

FILES_${PN}-rtl_bt8192 = " \
	${nonarch_base_libdir}/firmware/rtl_bt/rtl8192*.bin \
"

FILES_${PN}-rtl_bt8761 = " \
	${nonarch_base_libdir}/firmware/rtl_bt/rtl8761*.bin \
"

FILES_${PN}-rtl_bt8812 = " \
	${nonarch_base_libdir}/firmware/rtl_bt/rtl8812*.bin \
"

RDEPENDS_${PN}-rtl_bt8723 += " \
	${PN}-rtl-license \
	${PN}-whence-license \
"

RDEPENDS_${PN}-rtl_bt8821 += " \
	${PN}-rtl-license \
	${PN}-whence-license \
"

RDEPENDS_${PN}-rtl_bt8822 += " \
	${PN}-rtl-license \
	${PN}-whence-license \
"

RDEPENDS_${PN}-rtl_bt8192 += " \
	${PN}-rtl-license \
	${PN}-whence-license \
"

RDEPENDS_${PN}-rtl_bt8761 += " \
	${PN}-rtl-license \
	${PN}-whence-license \
"

RDEPENDS_${PN}-rtl_bt8812 += " \
	${PN}-rtl-license \
	${PN}-whence-license \
"

PACKAGES =+ " \
	${PN}-rtl_bt8723 \
	${PN}-rtl_bt8821 \
	${PN}-rtl_bt8822 \
	${PN}-rtl_bt8192 \
	${PN}-rtl_bt8761 \
	${PN}-rtl_bt8812 \
"
