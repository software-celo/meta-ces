require ces-console-base.inc

QT_MINIMAL_PACKAGES_INSTALL = " \
	qtbase \
	qtbase-plugins \
	qtsvg-plugins \
	qtgraphicaleffects-qmlplugins \
	qtwebengine-qmlplugins \
	qtdeclarative \
	qtdeclarative-qmlplugins \
	qtquickcontrols-qmlplugins \
	qtwebengine \
	qt5-opengles2-test \
	qtvirtualkeyboard \
"

QT_BROWSER_PACKAGES_INSTALL = " \
	ces-qt-browser \
	getting-started \
"

IMAGE_INSTALL_append = " \
	${QT_MINIMAL_PACKAGES_INSTALL} \
	${QT_BROWSER_PACKAGES_INSTALL} \
"
