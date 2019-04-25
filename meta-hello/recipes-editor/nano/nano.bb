SUMMARY = "Recipe to build the 'nano' editor"

PN = "nano"
PV = "2.2.5"

HOMEPAGE = "http://www.nano-editor.org/"
PV_MAJOR = "${@d.getVar('PV',1).split('.')[0]}.${@d.getVar('PV',1).split('.')[1]}"

SRC_URI = "http://www.nano-editor.org/dist/v${PV_MAJOR}/nano-${PV}.tar.gz"
SRC_URI[md5sum] = "77a10a49589f975ce98350a4527a2ebf"
SRC_URI[sha256sum] = "9015945d1badabbada203b37c4779d3dd1066234235c714deb439989c5cd7d9e"

python do_fetch() {
    bb.plain("Downloading ${SRC_URI}")
    src_uri = (d.getVar('SRC_URI', True) or "").split()

    if len(src_uri) == 0:
        bb.fatal("Empty URI")
    try:
        fetcher = bb.fetch2.Fetch(src_uri, d)
        fetcher.download()
    except bb.fetch2.BBFetchException:
        bb.fatal("Could not fetch source tarball.")
}
addtask fetch before do_build

python do_unpack() {
    bb.plain("Unpackin")
    os.system("tar x -C ${WORKDIR} -f ${DL_DIR}/${P}.tar.gz")
    bb.plain("Unpacked source")
}
addtask unpack before do_build after do_fetch

python do_configure() {
    bb.plain("Configuring")
    os.system("cd ${WORKDIR}/${P} && ./configure")
    bb.plain("Configured")
}
addtask configure before do_build after do_unpack

python do_compile() {
    bb.plain("Compiling")
    os.system("cd ${WORKDIR}/${P} && make")
    bb.plain("Compiled")
}
addtask compile before do_build after do_configure

