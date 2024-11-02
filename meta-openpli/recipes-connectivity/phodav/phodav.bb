SUMMARY = "Phodav WebDAV server"
DESCRIPTION = "phởdav (phodav) is a minimal WebDAV server implementation using GNOME libsoup (RFC 4918.)"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

inherit gitpkgv meson pkgconfig

PV = "1.0+git"
PKGV = "1.0+git${GITPKGV}"
#PR = "r0"

SRC_URI = "git://gitlab.gnome.org/GNOME/phodav.git;protocol=https;branch=master"
SRCREV = "2099147691fa98f37afa9196d5b7b9bd94c81c4c"

S = "${WORKDIR}/git"

DEPENDS = "avahi libsoup-2.4"
