#create the deployment directory-tree

#IMAGE_ROOTFS = "${WORKDIR}/${IMAGE_NAME}${PV}/rootfs"

#U_BOOT_EXT = "bin"
U_BOOT_EXT_mx6 = "imx"
U_BOOT_SYMLINK = "u-boot-${MACHINE}.${U_BOOT_EXT}"
U_BOOT_BINARY = "u-boot.${U_BOOT_EXT}"

CHRIST_UPDATE_CUSTOM_FILE ?= "${THISDIR}/custom.sh"
CHRIST_UPDATE_CCLIB_FILE ?= "${THISDIR}/cclib.sh"
CHRIST_UPDATE_ADD_FILES ?= ""

#we need some native tools for deployment
#DEPENDS_append = " imx-loader-native"
DEPENDS_append = " p7zip-native coreutils-native"
#we need mkimage to produce the U-Boot scripts even if not building a uImage kernel
DEPENDS_append = " virtual/bootloader u-boot-mkimage-native"

imagedeploy_kernel() {
    cp -p ${DEPLOY_DIR_IMAGE}/zImage-${MACHINE}.bin ${IMAGE_ROOTFS}/../${MACHINE}_bin/zImage
    cp -pP ${DEPLOY_DIR_IMAGE}/*.dtb ${IMAGE_ROOTFS}/../${MACHINE}_bin/
}

#*/

fakeroot do_imagedeploy() {

    if ! [ -d ${IMAGE_ROOTFS}/../${MACHINE}_bin ] ; then
        mkdir ${IMAGE_ROOTFS}/../${MACHINE}_bin
    fi

    imagedeploy_kernel

    cp -pP ${DEPLOY_DIR_IMAGE}/u-boot* ${IMAGE_ROOTFS}/../${MACHINE}_bin/
    mv ${IMAGE_ROOTFS}/../${MACHINE}_bin/${U_BOOT_SYMLINK} ${IMAGE_ROOTFS}/../${MACHINE}_bin/${U_BOOT_BINARY}

#    add the rootfs version to the welcome banner
#    echo "${IMAGE_NAME}${PV}_${DATE}" >> ${IMAGE_ROOTFS}/etc/issue
#    echo "" >> ${IMAGE_ROOTFS}/etc/issue

    sha1sum ${IMAGE_ROOTFS}/../${MACHINE}_bin/zImage ${IMAGE_ROOTFS}/../${MACHINE}_bin/zImage-${KERNEL_DEVICETREE} ${IMAGE_ROOTFS}/../${MACHINE}_bin/${U_BOOT_BINARY} \
        ${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}-${MACHINE}.tar.bz2 > ${IMAGE_ROOTFS}/../${MACHINE}_bin/sha1sums

    sed -i "s/\/.*\///g" ${IMAGE_ROOTFS}/../${MACHINE}_bin/sha1sums

    NO_UBOOT_FILE=""
    if ! [ "1" = ${CHRIST_UPDATE_INCLUDE_UBOOT} ]; then
	touch ${IMAGE_ROOTFS}/../${MACHINE}_bin/NO_UBOOT
	NO_UBOOT_FILE="${IMAGE_ROOTFS}/../${MACHINE}_bin/NO_UBOOT"
    fi

    CUSTOM_FILE=""
    if ! [ "x" = "x${CHRIST_UPDATE_INCLUDE_CUSTOM}" ]; then
        if [ "1" = "${CHRIST_UPDATE_INCLUDE_CUSTOM}" ]; then
            if [ -e "${CHRIST_UPDATE_CUSTOM_FILE}" ]; then
                cp "${CHRIST_UPDATE_CUSTOM_FILE}" ${IMAGE_ROOTFS}/../${MACHINE}_bin/custom.sh
                CUSTOM_FILE="${IMAGE_ROOTFS}/../${MACHINE}_bin/custom.sh"
            fi
        fi
    fi

    CCLIB_FILE=""
    if ! [ "x" = "x${CHRIST_UPDATE_INCLUDE_CCLIB}" ]; then
        if [ "1" = "${CHRIST_UPDATE_INCLUDE_CCLIB}" ]; then
            if [ -e "${CHRIST_UPDATE_CCLIB_FILE}" ]; then
                cp "${CHRIST_UPDATE_CCLIB_FILE}" ${IMAGE_ROOTFS}/../${MACHINE}_bin/cclib.sh
                CCLIB_FILE="${IMAGE_ROOTFS}/../${MACHINE}_bin/cclib.sh"
            fi
        fi
    fi

    ONLY_CUSTOM_FILE=""
    if [ "1" = ${CHRIST_UPDATE_ONLY_CUSTOM} ]; then
	touch ${IMAGE_ROOTFS}/../${MACHINE}_bin/ONLY_CUSTOM
	ONLY_CUSTOM_FILE="${IMAGE_ROOTFS}/../${MACHINE}_bin/ONLY_CUSTOM"
    fi

    CUSTOM_ADD_FILES=${CHRIST_UPDATE_ADD_FILES}

    7z a -l ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}-${PV}.7z ${IMAGE_ROOTFS}/../${MACHINE}_bin/zImage ${IMAGE_ROOTFS}/../${MACHINE}_bin/zImage-${KERNEL_DEVICETREE} \
        ${IMAGE_ROOTFS}/../${MACHINE}_bin/${U_BOOT_BINARY} ${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}-${MACHINE}.tar.bz2 ${IMAGE_ROOTFS}/../${MACHINE}_bin/sha1sums \
        ${NO_UBOOT_FILE} ${CUSTOM_FILE} ${ONLY_CUSTOM_FILE} ${CCLIB_FILE} ${CUSTOM_ADD_FILES}

}

addtask imagedeploy after do_image_complete before do_build
