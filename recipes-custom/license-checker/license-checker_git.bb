# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

SUMMARY = "Check license info for a package"
# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
#   node_modules/abbrev/LICENSE
#   node_modules/normalize-package-data/LICENSE
#   node_modules/fs.realpath/LICENSE
#   node_modules/glob/LICENSE
#   node_modules/balanced-match/LICENSE.md
#   lib/license.js
#   lib/license-files.js
#   tests/license.js
#   tests/license-files-test.js
#   bin/license-checker
#
# NOTE: multiple licenses have been detected; they have been separated with &
# in the LICENSE value for now since it is a reasonable assumption that all
# of the licenses apply. If instead there is a choice between the multiple
# licenses then you should change the value to separate the licenses with |
# instead of &. If there is any doubt, check the accompanying documentation
# to determine which situation is applicable.
LICENSE = "Unknown & MIT & Apache-2.0 & ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5ce085733d14fd58b61bcf9e27bc020d \
                    file://node_modules/color-name/LICENSE;md5=d301869b39e08b33665b7c4f16b8e41d \
                    file://node_modules/resolve/LICENSE;md5=baa47288b5bd3e657a01886ce3dd0cb6 \
                    file://node_modules/spdx-correct/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
                    file://node_modules/escape-string-regexp/license;md5=a12ebca0510a773644101a99a867d210 \
                    file://node_modules/validate-npm-package-license/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
                    file://node_modules/json-parse-better-errors/LICENSE.md;md5=048cc2006d506256349ca6088641486b \
                    file://node_modules/brace-expansion/LICENSE;md5=a5df515ef062cc3affd8c0ae59c059ec \
                    file://node_modules/wrappy/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
                    file://node_modules/treeify/LICENSE;md5=5ac376d4b5b9f6250ec17034df3afb85 \
                    file://node_modules/read-package-json/LICENSE;md5=6392dc4522c189c9616b2ec1ab763bec \
                    file://node_modules/minimist/LICENSE;md5=aea1cde69645f4b99be4ff7ca9abcce1 \
                    file://node_modules/graceful-fs/LICENSE;md5=fd63805fd8e3797063b247781e5ee6e4 \
                    file://node_modules/nopt/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
                    file://node_modules/asap/LICENSE.md;md5=6ef000dc4ca2360ae9216d401862c653 \
                    file://node_modules/array-find-index/license;md5=a12ebca0510a773644101a99a867d210 \
                    file://node_modules/slide/LICENSE;md5=90a3ca01a5efed8b813a81c6c8fa2e63 \
                    file://node_modules/debuglog/LICENSE;md5=40a8cc092438d1ad43c1146547c36218 \
                    file://node_modules/once/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
                    file://node_modules/util-extend/LICENSE;md5=d7351a4fc8e956f1a68413490d5e655e \
                    file://node_modules/npm-normalize-package-bin/LICENSE;md5=89966567781ee3dc29aeca2d18a59501 \
                    file://node_modules/path-parse/LICENSE;md5=4b940f9668dfcb796d2cb98ad94692df \
                    file://node_modules/readdir-scoped-modules/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
                    file://node_modules/semver/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
                    file://node_modules/spdx-satisfies/LICENSE;md5=4c804e8d23d30ae1cef9bab6c77dbe56 \
                    file://node_modules/os-tmpdir/license;md5=a12ebca0510a773644101a99a867d210 \
                    file://node_modules/concat-map/LICENSE;md5=aea1cde69645f4b99be4ff7ca9abcce1 \
                    file://node_modules/abbrev/LICENSE;md5=e9c0b639498fbe60d17b10099aba77c0 \
                    file://node_modules/normalize-package-data/LICENSE;md5=745d0cb7803882ab26451fefb149878e \
                    file://node_modules/ansi-styles/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
                    file://node_modules/os-homedir/license;md5=a12ebca0510a773644101a99a867d210 \
                    file://node_modules/spdx-ranges/LICENSE.md;md5=dad801eea414483e06c5792a77fbebc2 \
                    file://node_modules/chalk/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
                    file://node_modules/inherits/LICENSE;md5=5b2ef2247af6d355ae9d9f988092d470 \
                    file://node_modules/minimatch/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
                    file://node_modules/fs.realpath/LICENSE;md5=062470525c8e380f8567f665ef554d11 \
                    file://node_modules/supports-color/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
                    file://node_modules/color-convert/LICENSE;md5=9bdadfc9fbb3ab8d5a6d591bdbd52811 \
                    file://node_modules/ms/license.md;md5=fd56fd5f1860961dfa92d313167c37a6 \
                    file://node_modules/inflight/LICENSE;md5=90a3ca01a5efed8b813a81c6c8fa2e63 \
                    file://node_modules/read-installed/LICENSE;md5=5b2ef2247af6d355ae9d9f988092d470 \
                    file://node_modules/debug/LICENSE;md5=ddd815a475e7338b0be7a14d8ee35a99 \
                    file://node_modules/spdx-expression-parse/LICENSE;md5=35a411d082d4487ab0e0287014cddf80 \
                    file://node_modules/mkdirp/LICENSE;md5=b2d989bc186e7f6b418a5fdd5cc0b56b \
                    file://node_modules/glob/LICENSE;md5=c727d36f28f2762b1011dd483aa1a191 \
                    file://node_modules/spdx-compare/LICENSE.md;md5=dad801eea414483e06c5792a77fbebc2 \
                    file://node_modules/dezalgo/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
                    file://node_modules/path-is-absolute/license;md5=a12ebca0510a773644101a99a867d210 \
                    file://node_modules/hosted-git-info/LICENSE;md5=a01f10cd299d5727263720d47cc2c908 \
                    file://node_modules/balanced-match/LICENSE.md;md5=7fa99ddc3424107350ca6e9a24552085 \
                    file://node_modules/osenv/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
                    file://node_modules/has-flag/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
                    file://lib/license.js;md5=a993b4ea3c48bea196642b4f636f99e4 \
                    file://lib/license-files.js;md5=5fd2b52e346e80756d6b5ceb087208ad \
                    file://tests/license.js;md5=c38f37c56d94f340ee9c6ee02c6109a5 \
                    file://tests/license-files-test.js;md5=1c626c765eee8af718ee21d1aa959652 \
                    file://bin/license-checker;md5=07b54d10f129654c257baa883aa08a89 \
                    file://package.json;md5=d983eb86b8e151cf2a05489d4c2e3eb1 \
                    file://node_modules/debug/package.json;md5=ae814002086b04e3fb94c930eedfdb59 \
                    file://node_modules/chalk/package.json;md5=a21f60da989d4bf1cd5396df6c638a98 \
                    file://node_modules/read-package-json/package.json;md5=46d1208a6dcbae0baafcb7ea46218bbe \
                    file://node_modules/spdx-exceptions/package.json;md5=39574483a6be9b11884d0cd2812d3cef \
                    file://node_modules/semver/package.json;md5=735f6ede2a644d292066a9813d8b9da6 \
                    file://node_modules/normalize-package-data/package.json;md5=ef1b0b1c2f14d0c0b61b193ead64bc5b \
                    file://node_modules/inflight/package.json;md5=85ba25624378c23e1ee9b33d3d103bf0 \
                    file://node_modules/color-name/package.json;md5=7599aecb8597ca603c711d49a83dab59 \
                    file://node_modules/spdx-compare/package.json;md5=86b190885492e4e63a9fa92e0f45982a \
                    file://node_modules/has-flag/package.json;md5=73cc8d1e96c19ce85a7abb8f9468a86c \
                    file://node_modules/spdx-correct/package.json;md5=1049ac5d0310ce8a7ab8fb66837e178e \
                    file://node_modules/dezalgo/package.json;md5=c43d0615ab52f152f683893b4f975af1 \
                    file://node_modules/validate-npm-package-license/package.json;md5=e8168fa784142fe417f82359aeffc444 \
                    file://node_modules/once/package.json;md5=afb6ea3bdcad6397e11a71615bd06e3b \
                    file://node_modules/minimist/package.json;md5=84505571ecc56b8071068f44de7c79b2 \
                    file://node_modules/npm-normalize-package-bin/package.json;md5=8beb5d87634212035f1a5fe378e22fe4 \
                    file://node_modules/os-homedir/package.json;md5=cfc8a815cbc6e35583981f3006bfabd5 \
                    file://node_modules/util-extend/package.json;md5=0511b6ea57807d2f68ea78fd2ad431c8 \
                    file://node_modules/ansi-styles/package.json;md5=50f97f15be9377b1110c7a68f76fff0f \
                    file://node_modules/resolve/package.json;md5=32a1b5e1e08fa2264cf703dc0c500708 \
                    file://node_modules/glob/package.json;md5=7d0807efb6353ee0196cabbacbf210d7 \
                    file://node_modules/fs.realpath/package.json;md5=3aa3d67ce378e330e293496dd3b9a506 \
                    file://node_modules/wrappy/package.json;md5=788804d507f3ed479ea7614fa7d3f1a5 \
                    file://node_modules/spdx-license-ids/package.json;md5=6669a4252e592f230342ff0bbb963215 \
                    file://node_modules/slide/package.json;md5=ee0233987ed268585eb486bd842677d3 \
                    file://node_modules/array-find-index/package.json;md5=0a444c011d895396a227bb3ac1282fcd \
                    file://node_modules/balanced-match/package.json;md5=a2181cb8cab70df2084e1bdce79a389b \
                    file://node_modules/minimatch/package.json;md5=b763d93b18d070a6449399d2e92d8c32 \
                    file://node_modules/debuglog/package.json;md5=8a870032cb53ebfae66e433d3c66ac50 \
                    file://node_modules/abbrev/package.json;md5=09144e5559c19012a5ad2b1cb548f188 \
                    file://node_modules/path-parse/package.json;md5=63b66a2ecf520db6b1a3246bafd6f4bc \
                    file://node_modules/read-installed/package.json;md5=65bde5f77bea47a523482d6485e21229 \
                    file://node_modules/asap/package.json;md5=4c974dfa11d66358b3f8610ffcad5478 \
                    file://node_modules/os-tmpdir/package.json;md5=20555f37b3809082d0562ce7f144a04a \
                    file://node_modules/supports-color/package.json;md5=79d88f702d689c72ebe1798d2853a96e \
                    file://node_modules/concat-map/package.json;md5=85d8a674998927862b17adef4aa6a7b1 \
                    file://node_modules/spdx-satisfies/package.json;md5=8e3a96b3ecfcb424840572bf0dda20ee \
                    file://node_modules/spdx-ranges/package.json;md5=ac238c805730a92e1703f74ed401c177 \
                    file://node_modules/readdir-scoped-modules/package.json;md5=2e216b2e91833488f5107f3072db9322 \
                    file://node_modules/hosted-git-info/package.json;md5=d9b6f36d7701b75d6c1c6bf6a5783e0d \
                    file://node_modules/brace-expansion/package.json;md5=effd91994b1b7ddb8a33060ad4541e6a \
                    file://node_modules/path-is-absolute/package.json;md5=ef6e018bdf67b82ab1285bc799b5367b \
                    file://node_modules/treeify/package.json;md5=149726a9a440a8723997cfca1fe1699b \
                    file://node_modules/inherits/package.json;md5=f73908dab55d4259f3ed052ce9fb2fbb \
                    file://node_modules/ms/package.json;md5=b3ea7267a23f72028e774742792b114a \
                    file://node_modules/graceful-fs/package.json;md5=b15a74dc8ed4402c8eca1c422ff94a26 \
                    file://node_modules/mkdirp/package.json;md5=3b5ba3c4a04a8b0520bd0d392cf1c48f \
                    file://node_modules/escape-string-regexp/package.json;md5=6050cf06c06dcb38dce670ff96b21aa9 \
                    file://node_modules/color-convert/package.json;md5=51a202f6ab9b016f0f7219701f1f5e70 \
                    file://node_modules/json-parse-better-errors/package.json;md5=f96227c6195a42d3513ad75a81997794 \
                    file://node_modules/spdx-expression-parse/package.json;md5=ed078a9f087528062b52072fc250eb66 \
                    file://node_modules/nopt/package.json;md5=5192f3f2450d866a1118c12915c38e37 \
                    file://node_modules/osenv/package.json;md5=4690af52479190b37d7e5df62fa167d8"

SRC_URI = " \
    git://github.com/davglass/license-checker.git;protocol=https \
    npmsw://${THISDIR}/${BPN}/npm-shrinkwrap.json \
    "

# Modify these as desired
PV = "25.0.1+git${SRCPV}"
SRCREV = "de6e9a42513aa38a58efc6b202ee5281ed61f486"

S = "${WORKDIR}/git"

inherit npm

LICENSE_${PN}-spdx-compare = "MIT"
LICENSE_${PN}-spdx-exceptions = "Unknown"
LICENSE_${PN}-os-tmpdir = "MIT"
LICENSE_${PN}-debuglog = "MIT"
LICENSE_${PN}-hosted-git-info = "ISC"
LICENSE_${PN}-path-is-absolute = "MIT"
LICENSE_${PN}-graceful-fs = "ISC"
LICENSE_${PN}-inflight = "ISC"
LICENSE_${PN}-has-flag = "MIT"
LICENSE_${PN}-debug = "MIT"
LICENSE_${PN}-json-parse-better-errors = "MIT"
LICENSE_${PN}-once = "ISC"
LICENSE_${PN}-minimist = "MIT"
LICENSE_${PN}-glob = "Unknown"
LICENSE_${PN}-wrappy = "ISC"
LICENSE_${PN}-dezalgo = "ISC"
LICENSE_${PN}-spdx-ranges = "MIT"
LICENSE_${PN}-validate-npm-package-license = "Apache-2.0"
LICENSE_${PN}-mkdirp = "MIT"
LICENSE_${PN}-spdx-license-ids = "Unknown"
LICENSE_${PN}-balanced-match = "Unknown"
LICENSE_${PN}-chalk = "MIT"
LICENSE_${PN}-supports-color = "MIT"
LICENSE_${PN}-fsrealpath = "Unknown"
LICENSE_${PN}-inherits = "ISC"
LICENSE_${PN}-color-name = "MIT"
LICENSE_${PN}-slide = "ISC"
LICENSE_${PN}-escape-string-regexp = "MIT"
LICENSE_${PN}-spdx-satisfies = "MIT"
LICENSE_${PN}-color-convert = "MIT"
LICENSE_${PN}-read-package-json = "ISC"
LICENSE_${PN} = "Unknown"
LICENSE_${PN}-nopt = "ISC"
LICENSE_${PN}-minimatch = "ISC"
LICENSE_${PN}-util-extend = "MIT"
LICENSE_${PN}-resolve = "MIT"
LICENSE_${PN}-brace-expansion = "MIT"
LICENSE_${PN}-ms = "MIT"
LICENSE_${PN}-ansi-styles = "MIT"
LICENSE_${PN}-concat-map = "MIT"
LICENSE_${PN}-spdx-expression-parse = "MIT"
LICENSE_${PN}-readdir-scoped-modules = "ISC"
LICENSE_${PN}-path-parse = "MIT"
LICENSE_${PN}-normalize-package-data = "Unknown"
LICENSE_${PN}-spdx-correct = "Apache-2.0"
LICENSE_${PN}-npm-normalize-package-bin = "ISC"
LICENSE_${PN}-semver = "ISC"
LICENSE_${PN}-treeify = "MIT"
LICENSE_${PN}-os-homedir = "MIT"
LICENSE_${PN}-osenv = "ISC"
LICENSE_${PN}-array-find-index = "MIT"
LICENSE_${PN}-abbrev = "Unknown"
LICENSE_${PN}-read-installed = "ISC"
LICENSE_${PN}-asap = "MIT"
BBCLASSEXTEND = "native"
