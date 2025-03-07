SUMMARY = "EDID decoder and conformance tester"
DESCRIPTION = "edid-decode decodes EDID monitor description data in human-readable format"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;beginline=4;endline=9;md5=fe88683fee2925352e93000158b761ac"

inherit gitpkgv

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"

SRC_URI = "git://git.linuxtv.org/edid-decode.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit meson pkgconfig
