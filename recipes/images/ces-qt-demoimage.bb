SUMMARY = "Image booting a minimal qt5 browser"

require image-base/ces-qt-base.inc

PV = "V3.0"
PR = "r0"

# The following variables can be used to control the image
# update via CURT.
#
# Set _INCLUDE_UBOOT to "1" in order to update the U-Boot.
# Set _INCLUDE_CUSTOM to "1" in order to execute the
# custom.sh script after the update process is ready.
# Set _ONLY_CUSTOM to "1" in order to disable all other
# update tasks and only run the custom.sh script.
# Use _CUSTOM_FILE to specify a path to your custom.sh
# script. Default is ${THISDIR}/custom.sh
# -> meta-ces/recipes/images/custom.sh
# Use _UPDATE_ADD_FILES to include additional files in
# the update archive. May contain multiple files separated
# by whitespaces.
# Set _INCLUDE_CCLIB to "1" in order to override curt
# internal functions. There are some pitfalls you have to
# consider when using this. Please contact us if you want
# to use these functions.
# Use _CCLIB_FILE to specify a path to your cclib.sh
# script. Default is ${THISDIR}/cclib.sh
# -> meta-ces/recipes/images/cclib.sh

CHRIST_UPDATE_INCLUDE_UBOOT = "0"
CHRIST_UPDATE_INCLUDE_CUSTOM = "0"
CHRIST_UPDATE_ONLY_CUSTOM = "0"
# CHRIST_UPDATE_CUSTOM_FILE = "${THISDIR}/custom.sh"
# CHRIST_UPDATE_ADD_FILES = ""
CHRIST_UPDATE_INCLUDE_CCLIB = "0"
# CHRIST_UPDATE_CCLIB_FILE = "${THISDIR}/cclib.sh"

IMAGE_FEATURES += "allow-root-login"

export IMAGE_BASENAME = "ces-qt-demoimage"
