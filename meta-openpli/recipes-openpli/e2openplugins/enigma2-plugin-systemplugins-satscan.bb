MODULE = "Satscan"
DESCRIPTION = "Blind scan on DVB-S"
RDEPENDS:${PN} = "virtual-blindscan-dvbs"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit gitpkgv

PV = "1.3+git"
PKGV = "${PV}"
PR = "r0"

require conf/license/license-gplv2.inc
require openplugins-distutils.inc
