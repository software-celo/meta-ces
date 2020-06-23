CHRIST_UPDATE_CCLIB_FILE := "${THISDIR}/cclib-rauc.sh"

IMAGE_INSTALL_ces-fb-rauc_append += " \
	python3-aiohttp \
	python3-async-timeout \
	python3-idna-ssl \
	rauc \
	rauc-hawkbit \
	rauc-hawkbit-updater \
	kernel-devicetree \
	util-linux \
	e2fsprogs \
"

IMAGE_INSTALL_ces-fb-rauc_remove += " \
	systemd-bootcheck \
	enable-update \
"                       
