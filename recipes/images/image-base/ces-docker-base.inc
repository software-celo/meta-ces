DOCKER_PACKAGES_INSTALL = " \
    docker-ce \
"

IMAGE_INSTALL_append = " \
	${DOCKER_PACKAGES_INSTALL} \
"

