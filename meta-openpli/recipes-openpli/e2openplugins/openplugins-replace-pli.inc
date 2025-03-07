# Open version should replace old PLi version of the same plugin. This
# include file arranges that the Open version is newer, and that the
# old PLi plugin is removed on install.
OLDPKG = "${@d.getVar('PN', True).replace('-extensions-','-pli-')}"
RREPLACES:${PN} = "${OLDPKG}"
RCONFLICTS:${PN} = "${OLDPKG}"

inherit gitpkgv
PV = "2.0+git"
PKGV = "2.0+git${GITPKGV}"
PR = "r1"
