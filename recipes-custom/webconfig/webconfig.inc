SUMMARY  = "Christ Webconfiguration Interface"
DESCRIPTION = "Small nodejs based html webconfiguration interface for devicesettings on Christ iMX6 based platforms"
AUTHOR = "Steffen Kothe <skothe@christ-es.de>"
HOMEPAGE = "https://www.christ-es.de"
LICENSE = "MIT"
SECTION = "web"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1bcb54d6f4877c88b3a20f836d45f56c"

PR = "r0"
FILESEXTRAPATHS_prepend := "${THISDIR}/webconfig:"
FILESEXTRAPATHS_prepend := "${THISDIR}/webconfig/patches:"
FILESEXTRAPATHS_prepend := "${THISDIR}/webconfig/localpage:"

WEBCONFIG_SRC_URI ?= ""

inherit npm

S = "${WORKDIR}/git"
SRC_URI = " \
	${WEBCONFIG_SRC_URI} \
	file://webconfig.service \
	file://footer.png \
	file://header-logo-center.png \
	file://header-logo-left.png \
	file://header-logo-right.png \
	file://index.html \
	file://script.js \
	file://style.css \
"

STANDARD_LOCAL_PAGE ?= "true"
CUSTOM_OEM_LOGO ?= ""
CUSTOM_OEM_FAVICON ?= ""

BACKEND_PATH = "${sbindir}"
CONFIGFILE_PATH = "${sysconfdir}/webconfig"
WEBCONFIG_PATH = "${bindir}/webconfig"
CLIENT_PATH  = "${WEBCONFIG_PATH}/client"
WEBCONFIG_LOCAL_PATH = "${datadir}/webconfig/local"
DEFAULT_SAVE_PATH = "${sysconfdir}/default"
 
do_compile (){
	#Download modules for webconfig
	npm install
	
	#Compile webconfig
	npm run build

	#Removes not needed packages for production build 
	npm prune --production
}


do_install (){
	
	#Install standardpaths for webconfig
	install -d ${D}${BACKEND_PATH}		#/usr/sbin
	install -d ${D}${CONFIGFILE_PATH}	#/etc/webconfig
	install -d ${D}${WEBCONFIG_PATH}	#/bin/webconfig
	install -d ${D}${WEBCONFIG_LOCAL_PATH}  #/usr/share/webconfig/local
	install -d ${D}${DEFAULT_SAVE_PATH}	#/etc/default

	#Install default systemd paths for services
	install -d ${D}${systemd_unitdir}/system
	install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants

	#Install webconfig service
	install -m 0644 ${WORKDIR}/webconfig.service ${D}${systemd_unitdir}/system
	ln -sf ${D}${systemd_unitdir}/system/webconfig.service \
		${D}${sysconfdir}/systemd/system/multi-user.target.wants	

	#Install backend files and webconfig to image
	cp -a ${S}/server/backend/pixi-crix/. ${D}${BACKEND_PATH}
	chmod 744 -R ${D}${BACKEND_PATH} 
	cp -a ${S}/server/default_config_files/. ${D}${CONFIGFILE_PATH}
	install -m 0000 ${S}/server/default_config_files/login.json ${D}${DEFAULT_SAVE_PATH}
	cp -a ${S}/dist/. ${D}${WEBCONFIG_PATH}

	install_custom_oem	
	install_localpage
}


install_custom_oem () {

	major=`echo ${PV} | cut -d'.' -f1`
	minor=`echo ${PV} | cut -d'.' -f2`

	if [ -n "${CUSTOM_OEM_LOGO}" ] && [ -n "${CUSTOM_OEM_FAVICON}" ];then
		if [ ${minor}${minor} -gt 12 ] ;then
			rm -f ${D}${CLIENT_PATH}/image_icons/headerlogo.png
			rm -f ${D}${CLIENT_PATH}/favicon.ico

			logo_oem_extension=`echo ${CUSTOM_OEM_LOGO} | cut -d'.' -f2`
			favicon_oem_extension=`echo ${CUSTOM_OEM_FAVICON} | cut -d'.' -f2`

			if [ "${logo_oem_extension}" != "png" ];then
				bberror "CUSTOM_OEM_LOGO: Extension failure: .png required!"
			fi

			if [ "${favicon_oem_extension}" != "ico" ] && [ "${favicon_oem_extension}" != "ICO" ]; then
				bberror "CUSTOM_FAVICON_LOGO: Extension failure: .ico or .ICO required!"
			fi

			#Install directory first, cp is not enough to stat directory (see do_install)
			install -d ${D}${CLIENT_PATH}/images_icons
			install -m 0644 ${WORKDIR}/${CUSTOM_OEM_LOGO} ${D}${CLIENT_PATH}/images_icons/headerlogo.png
			install -m 0644 ${WORKDIR}/${CUSTOM_OEM_FAVICON} ${D}${CLIENT_PATH}/favicon.ico
		else
			bbwarn "OEM Logo customization is not supported by webconfig version ${PV}"
		fi
	elif [ -n "${CUSTOM_OEM_LOGO}" ] || [ -n "${CUSTOM_OEM_FAVICON}" ];then
		if [ -z "${CUSTOM_OEM_LOGO}" ];then
			bberror "CUSTOM_OEM_LOGO: Set filename and file:// in recipe SRC_URI"
		fi
		if [ -z "${CUSTOM_OEM_FAVICON}" ];then
			bberror "CUSTOM_OEM_FAVICON: Set Filename and file:// in recipe SRC_URI"
		fi
	else
		bbnote "Leave default webconfig logo"
	fi
}

install_localpage() {
	#Install local page for webconfig

	if [ "${STANDARD_LOCAL_PAGE}" = "true" ]; then
		bbnote "Install default localpage"
		install -m 0644 ${WORKDIR}/footer.png ${D}${WEBCONFIG_LOCAL_PATH}
		install -m 0644 ${WORKDIR}/header-logo-center.png ${D}${WEBCONFIG_LOCAL_PATH}
		install -m 0644 ${WORKDIR}/header-logo-left.png ${D}${WEBCONFIG_LOCAL_PATH}
		install -m 0644 ${WORKDIR}/header-logo-right.png ${D}${WEBCONFIG_LOCAL_PATH}
		install -m 0644 ${WORKDIR}/index.html ${D}${WEBCONFIG_LOCAL_PATH}
		install -m 0644 ${WORKDIR}/script.js ${D}${WEBCONFIG_LOCAL_PATH}
		install -m 0644 ${WORKDIR}/style.css ${D}${WEBCONFIG_LOCAL_PATH}
	else 
		bbnote  "Do not install default localpage"
	fi
}

FILES_${PN} = " \
	${BACKEND_PATH} \
	${CONFIGFILE_PATH}  \
	${WEBCONFIG_PATH} \
	${WEBCONFIG_LOCAL_PATH} \
	${DEFAULT_SAVE_PATH} \
	${systemd_unitdir}/system \
	${sysconfdir}/systemd/system/multi-user.target.wants \
"

#Dependencies for runtime
RDEPENDS_${PN} += " \
	nodejs (>= 7.10.0) \
	busybox (>= 1.24.1) \
	nodejs-npm \
	systemd \
	bash \
	grep \
	ces-qt-browser (>=2.0) \
"

#Dependencies for compiling/building
DEPENDS_${PN} = "nodejs-native"
