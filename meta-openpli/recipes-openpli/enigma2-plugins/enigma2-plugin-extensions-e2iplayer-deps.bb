SUMMARY = "Meta package for installing all dependencies for SSS' E2i Player"
inherit allarch python3-compileall

require conf/license/license-gplv2.inc

RRECOMMENDS:${PN} = " \
	ffmpeg \
	exteplayer3 \
	gstplayer \
	wget \
	hlsdl \
	lsdir \
	f4mdump \
	gst-ifdsrc \
	iptvsubparser \
	rtmpdump \
	duktape-e2iplayer \
	uchardet \
	"

PV = "1.0"
PR = "r0"

ALLOW_EMPTY:${PN} = "1"
