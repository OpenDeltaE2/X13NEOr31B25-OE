SUMMARY = "Kodi Media Center"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=7b423f1c9388eae123332e372451a4f7"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-21:"

PACKAGE_ARCH = "${MACHINE}"

inherit ccache cmake gettext pkgconfig python3targetconfig

DEPENDS += " \
            fmt \
            flatbuffers flatbuffers-native \
            fstrcmp \
            rapidjson \
            crossguid \
            libdvdnav libdvdcss libdvdread libudfread \
            ffmpeg \
            git-native \
            curl-native \
            gperf-native \
            jsonschemabuilder-native \
            nasm-native \
            swig-native \
            unzip-native \
            yasm-native \
            zip-native \
            \
            avahi \
            bzip2 \
            curl \
            libdcadec \
            faad2 \
            fontconfig \
            fribidi \
            glib-2.0 \
            giflib \
            libass \
            libcdio \
            libcec \
            libinput \
            libbluray \
            libmicrohttpd \
            libnfs \
            libpcre \
            libplist \
            libsquish \
            libssh \
            spdlog \
            libtinyxml \
            libtinyxml2 \
            libxkbcommon \
            libxml2 \
            libxslt \
            lzo \
            mpeg2dec \
            python3 \
            samba \
            sqlite3 \
            taglib \
            virtual/egl \
            wavpack \
            zlib \
            texturepacker-native \
            \
            gstreamer1.0 \
            gstreamer1.0-plugins-base \
          "
inherit gitpkgv
# 21.0 Omega
SRCREV = "068f0f694f28179549fcc3c99df237c26b8876d6"

# 'patch' doesn't support binary diffs
PATCHTOOL = "git"

PR = "r0"

PV = "21.0+gitr"
PV_groovy = "4.0.18"
PV_commons-lang3 = "3.16.0"
PV_commons-text = "1.12.0"

SRC_URI[groovy.sha256sum] = "4b03aa472ec7848d272893348a656be05d1b3502b30770ea57efa158e61154a6"
SRC_URI[commons-lang.sha256sum] = "0c97775a9e8a37b1e4cfd0993ad27c28bf6fc2611857a80ff5769805cea53a0a"
SRC_URI[commons-text.sha256sum] = "265a149c7e0c1ebfe019bbe0226f8c1f6474811054d459145510ea2eed93a11a"

SRC_URI = "git://github.com/xbmc/xbmc.git;protocol=https;branch=Omega \
           https://archive.apache.org/dist/groovy/${PV_groovy}/distribution/apache-groovy-binary-${PV_groovy}.zip;name=groovy \
           https://dlcdn.apache.org/commons/lang/binaries/commons-lang3-${PV_commons-lang3}-bin.tar.gz;name=commons-lang \
           https://dlcdn.apache.org/commons/text/binaries/commons-text-${PV_commons-text}-bin.tar.gz;name=commons-text \
           file://0001-flatbuffers-21.patch \
           file://0002-readd-Touchscreen-settings.patch \
           file://0003-shader-nopow-21.patch \
           file://0004-stb-settings-21.patch \
           file://0005-stb-support-21.patch \
           file://0006-add-winsystemfactory-windowing-init.patch \
           file://0007-adapt-window-system-registration.patch \
           file://0008-reinstate-system-h.patch \
           file://0009-reinstate-platform-defines.patch \
           file://0010-FindLibDvd.cmake-build-with-external-source.patch \
           file://0011-cmake-includedirs.patch \
           file://0012-taglib2.patch \
           file://0013-use-mute-button-for-ok-control.patch \
           file://0100-e2-player.patch \
           file://0101-gst-player.patch \
          "

S = "${WORKDIR}/git"

ACCEL ?= ""
ACCEL:x86 = "vaapi vdpau"
ACCEL:x86-64 = "vaapi vdpau"

# Default to GBM everywhere, sucks to be nvidia
WINDOWSYSTEM ?= "stb"

#[cmake] only use APP_RENDER_SYSTEM
#https://github.com/xbmc/xbmc/commit/d159837cf736c9ba17772ba52e4ce95aa3625528
APPRENDERSYSTEM ?= "gles"

TOOLCHAIN:arm ?= "clang"

PACKAGECONFIG ?= "${ACCEL} ${WINDOWSYSTEM} pulseaudio lcms lto \
                   ${@bb.utils.contains('TOOLCHAIN', 'clang', 'clang', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-lld', 'lld', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'opengl', 'openglesv2', d)}"

# Core windowing system choices

PACKAGECONFIG[x11] = "-DCORE_PLATFORM_NAME=x11,,libxinerama libxmu libxrandr libxtst glew"
PACKAGECONFIG[gbm] = "-DCORE_PLATFORM_NAME=gbm -DGBM_RENDER_SYSTEM=gles,,"
PACKAGECONFIG[stb] = "-DCORE_PLATFORM_NAME=stb,,"
PACKAGECONFIG[raspberrypi] = "-DCORE_PLATFORM_NAME=rbpi,,userland"
PACKAGECONFIG[amlogic] = "-DCORE_PLATFORM_NAME=aml,,"
PACKAGECONFIG[wayland] = "-DCORE_PLATFORM_NAME=wayland -DWAYLAND_RENDER_SYSTEM=gles,,wayland waylandpp"

PACKAGECONFIG[opengl] = "-DENABLE_OPENGL=ON,,"
PACKAGECONFIG[openglesv2] = "-DENABLE_GLES=ON,,virtual/egl"

PACKAGECONFIG[vaapi] = "-DENABLE_VAAPI=ON,-DENABLE_VAAPI=OFF,libva"
PACKAGECONFIG[vdpau] = "-DENABLE_VDPAU=ON,-DENABLE_VDPAU=OFF,libvdpau"
PACKAGECONFIG[mysql] = "-DENABLE_MYSQLCLIENT=ON,-DENABLE_MYSQLCLIENT=OFF,mysql5"
PACKAGECONFIG[pulseaudio] = "-DENABLE_PULSEAUDIO=ON,-DENABLE_PULSEAUDIO=OFF,pulseaudio"
PACKAGECONFIG[lcms] = ",,lcms"

# Compilation tunes

PACKAGECONFIG[lld] = "-DENABLE_LLD=ON,-DENABLE_LLD=OFF,llvm"
PACKAGECONFIG[clang] = "-DENABLE_CLANGFORMAT=ON -DENABLE_CLANGTIDY=ON,-DENABLE_CLANGFORMAT=OFF -DENABLE_CLANGTIDY=OFF,llvm"
PACKAGECONFIG[gold] = "-DENABLE_GOLD=ON,-DENABLE_GOLD=OFF"
PACKAGECONFIG[lto] = "-DUSE_LTO=${@oe.utils.cpu_count()},-DUSE_LTO=OFF"

LDFLAGS += "${TOOLCHAIN_OPTIONS}"
LDFLAGS:append:mipsarch = " -latomic"
EXTRA_OECMAKE:append:mipsarch = " -DWITH_ARCH=${TARGET_ARCH}"

KODI_DISABLE_INTERNAL_LIBRARIES = " \
  -DENABLE_INTERNAL_CROSSGUID=OFF \
  -DENABLE_INTERNAL_FLATBUFFERS=OFF \
  -DENABLE_INTERNAL_FMT=OFF \
  -DENABLE_INTERNAL_FSTRCMP=0 \
  -DENABLE_INTERNAL_RapidJSON=OFF \
  -DENABLE_INTERNAL_SPDLOG=OFF \
  -DENABLE_INTERNAL_FFMPEG=OFF \
"

# Allow downloads during internals build
do_compile[network] = "1"

RUNTIME:arm ?= "llvm"

RUNTIME_NM = "${@bb.utils.contains('RUNTIME', 'llvm', '${TARGET_PREFIX}llvm-nm', '${TARGET_PREFIX}gcc-nm', d)}"

EXTRA_OECMAKE = " \
    ${KODI_ARCH} \
    ${KODI_DISABLE_INTERNAL_LIBRARIES} \
    -DAPP_RENDER_SYSTEM=${APPRENDERSYSTEM} \
    \
    -DJava_JAVA_EXECUTABLE=/usr/bin/java \
    -DCLANG_TIDY_EXECUTABLE=${STAGING_BINDIR_NATIVE}/clang-tidy \
    -DCLANG_FORMAT_EXECUTABLE=${STAGING_BINDIR_NATIVE}/clang-format \
    \
    -DWITH_TEXTUREPACKER=${STAGING_BINDIR_NATIVE}/TexturePacker \
    -DWITH_JSONSCHEMABUILDER=${STAGING_BINDIR_NATIVE}/JsonSchemaBuilder \
    \
    -DENABLE_STATIC_LIBS=FALSE \
    -DCMAKE_NM=${STAGING_BINDIR_NATIVE}/${TARGET_SYS}/${RUNTIME_NM} \
    \
    -DFFMPEG_PATH=${STAGING_DIR_TARGET} \
    -DLIBDVD_INCLUDE_DIRS=${STAGING_INCDIR} \
    -DNFS_INCLUDE_DIR=${STAGING_INCDIR} \
    -DSHAIRPLAY_INCLUDE_DIR=${STAGING_INCDIR} \
    \
    -DENABLE_AIRTUNES=ON \
    -DENABLE_OPTICAL=OFF \
    -DENABLE_DVDCSS=OFF \
    -DENABLE_DEBUGFISSION=OFF \
    -DCMAKE_BUILD_TYPE=RelWithDebInfo \
    \
    ${@bb.utils.contains('MACHINE_FEATURES', 'v3d-cortexa15', '-DWITH_PLATFORM="v3d-cortexa15"', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'v3d-mipsel', '-DWITH_PLATFORM="v3d-mipsel"', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'edision-cortexa15', '-DWITH_PLATFORM="edision-cortexa15"', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'dags-cortexa15', '-DWITH_PLATFORM="dags-cortexa15"', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'xcore-mipsel', '-DWITH_PLATFORM="xcore-mipsel"', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'nextv-cortexa15', '-DWITH_PLATFORM="nextv-cortexa15"', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'GB-cortexa15', '-DWITH_PLATFORM="GB-cortexa15"', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'vuplus-mipsel', '-DWITH_PLATFORM="vuplus-mipsel"', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'vuplus-cortexa15', '-DWITH_PLATFORM="vuplus-cortexa15"', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'mali', '-DWITH_PLATFORM="mali-cortexa15"', '', d)} \
    \
    -Dgroovy_SOURCE_DIR=${WORKDIR}/groovy-${PV_groovy} \
    -Dapache-commons-lang_SOURCE_DIR=${WORKDIR}/commons-lang3-${PV_commons-lang3} \
    -Dapache-commons-text_SOURCE_DIR=${WORKDIR}/commons-text-${PV_commons-text} \
"

# OECMAKE_GENERATOR="Unix Makefiles"
# PARALLEL_MAKE = " "

FULL_OPTIMIZATION:armv7a = "-fomit-frame-pointer -O3 -ffast-math"
FULL_OPTIMIZATION:armv7ve = "-fomit-frame-pointer -O3 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

# for python modules
export HOST_SYS
export BUILD_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
export ${PYTHON_DIR}

export TARGET_PREFIX

do_configure:prepend() {
    # Ensure 'nm' can find the lto plugins
    liblto=$(find ${STAGING_DIR_NATIVE} -name "liblto_plugin.so.0.0.0")
    mkdir -p ${STAGING_LIBDIR_NATIVE}/bfd-plugins
    ln -sf $liblto ${STAGING_LIBDIR_NATIVE}/bfd-plugins/liblto_plugin.so

#    sed -i -e 's:CMAKE_NM}:}${TARGET_PREFIX}gcc-nm:' ${S}/xbmc/cores/DllLoader/exports/CMakeLists.txt
}

INSANE_SKIP:${PN} = "rpaths already-stripped textrel"

FILES:${PN} = "${libdir}/kodi ${libdir}/xbmc"
FILES:${PN} += "${bindir}/kodi ${bindir}/xbmc ${bindir}/kodi-TexturePacker"
FILES:${PN} += "${datadir}/icons ${datadir}/kodi ${datadir}/xbmc ${datadir}/applications"
FILES:${PN} += "${bindir}/kodi-standalone ${bindir}/xbmc-standalone ${datadir}/xsessions ${datadir}/metainfo"
FILES:${PN} += "${libdir}/firewalld"
FILES:${PN}-dev = "${includedir}"
FILES:${PN}-dbg += "${libdir}/kodi/.debug ${libdir}/kodi/*/.debug ${libdir}/kodi/*/*/.debug ${libdir}/kodi/*/*/*/.debug"

# kodi uses some kind of dlopen() method for libcec so we need to add it manually
# OpenGL builds need glxinfo, that's in mesa-demos
RRECOMMENDS:${PN}:append = " libcec \
                             libcurl \
                             libnfs \
                             nss \
                             os-release \
                             ${@bb.utils.contains('PACKAGECONFIG', 'x11', 'xrandr xinit mesa-demos', '', d)} \
                             python3 \
                             python3-compression \
                             python3-crypt \
                             python3-ctypes \
                             python3-datetime \
                             python3-db \
                             python3-image \
                             python3-difflib \
                             python3-html \
                             python3-json \
                             python3-mechanize \
                             python3-multiprocessing \
                             python3-netclient \
                             python3-pillow \
                             python3-profile \
                             python3-pycryptodome \
                             python3-pycryptodomex \
                             python3-regex \
                             python3-setuptools \
                             python3-shell \
                             python3-six \
                             python3-sqlite3 \
                             python3-xmlrpc \
                             tzdata-africa \
                             tzdata-americas \
                             tzdata-antarctica \
                             tzdata-arctic \
                             tzdata-asia \
                             tzdata-atlantic \
                             tzdata-australia \
                             tzdata-europe \
                             tzdata-pacific \
                             xkeyboard-config \
                             kodi-addon-inputstream-adaptive-omega \
                             kodi-addon-inputstream-rtmp-omega \
                             alsa-plugins \
                           "

RRECOMMENDS:${PN}:append:libc-glibc = " glibc-charmap-ibm850 \
                                        glibc-gconv-ibm850 \
                                        glibc-charmap-ibm437 \
                                        glibc-gconv-ibm437 \
                                        glibc-gconv-unicode \
                                        glibc-gconv-utf-32 \
                                        glibc-charmap-utf-8 \
                                        glibc-localedata-en-us \
                                      "
