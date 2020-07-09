CHRIST_UPDATE_CCLIB_FILE := "${THISDIR}/cclib-rauc.sh"

IMAGE_INSTALL_append_ces-fb-rauc += " \
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

IMAGE_INSTALL_remove_ces-fb-rauc += " \
	systemd-bootcheck \
	enable-update \
"                       
