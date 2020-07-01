#
#   SPDX-License-Identifier: MIT
#
#   Copyright (c) Christ Electronic Systems GmbH 2020-2020
#   Copyright (c) Steffen Kothe <skothe@christ-es.de> 2020-2020
#
#

# This class provides an automated mechanism to extract license information of the generated image for package related and npm related modules.
# By default, this class is not active. An active creation of xml, html and pdf manifest documents has to be forced with the explicit set of some
# variables.

LIC_XML_MANIFEST ?= "0"
LIC_DISABLE_HTML_TO_PDF ?= "0"
LIC_CES_TRANSFORM_FILE := "${THISDIR}/license-transform/ces-license-base.xsl"

#   Control deploy by fileextensions automatically. Wildcard for files is not provided!
#   By default, this variable is empty.
#
#   Example:
#   LIC_DEPLOY_BY_EXTENSION ?= " \
#           .html \
#           .json \
#           .xml \
#           .pdf \
#   "
#
#   This example deploys all files with the extensions 'html', 'pdf', 'xml' and 'json' to
#   the rootfs.
#
LIC_DEPLOY_BY_EXTENSION ?= ""

LIC_DEPLOY_BY_FILENAME ?= " \
        ces-license-base.html \
        ces-license-base.pdf \
        ${IMAGE_BASENAME}-manifest.xml \
"

def createPrettifiedXMLDoc (elem):

    import sys, re, json, chardet
    from xml.etree.ElementTree import Element, SubElement, Comment, tostring
    from xml.etree import ElementTree
    from xml.dom import minidom

    rough_string = ElementTree.tostring(elem, 'utf-8')
    reparsed = minidom.parseString(rough_string)
    return reparsed.toprettyxml(indent="    ", encoding="UTF-8")

def transformXMLviaXSLT (xmldoc, xsltdoc):
    import lxml.etree as LET

    try:
        dom = LET.parse (xmldoc)
        xslt = LET.parse (xsltdoc)
        procXSLT = LET.XSLT ( xslt )
        tdom = procXSLT ( dom )
    except Exception as e:
        bb.error("XML-transformation of %s via xls(t) %s failed!\n%s" % (xmldoc, xsltdoc, e))
        return None

    return tdom

def weakDomStringTypeCheck (domstring):
    import lxml.etree as LET
    import lxml.html as LHTML
    from io import StringIO, BytesIO
    import json as JSON
    extList = []
    selectedExt = None

    if isinstance (domstring, str) is not True:
        bb.fatal("Invalid type detected! domstring check not possible")

    try:
        htmlParser = LET.HTMLParser()
        htmlTree = LET.parse(StringIO(domstring), htmlParser)
        bb.debug(1, "DOCTYPE: %s", htmlTree.docinfo.doctype)
        bb.debug(1, "<body> tag detected: %s" % "False" if None == htmlTree.find("body").tag else "True")
        bb.debug(1, "<head> tag detected: %s" % "False" if None == htmlTree.find("head").tag else "True")

        if "html" not in htmlTree.docinfo.doctype:
            raise Exception("HTML Doctype defintion in head of document not found")

        extList.append(".html")

    except Exception as e:
        bb.debug(1, "HTML-validation failed")
        bb.debug(1, "%s" % e)

    try:
        xmlParser = LET.XMLParser()
        xmlTree = LET.parse(StringIO(domstring), xmlParser)

        if ( None == xmlTree.docinfo.xml_version and
             None == xmlTree.docinfo.encoding
           ):
            raise Exception("XML declaration of document <?xml encoding=? version=?> seems to be incorrect or not existent")

        extList.append(".xml")

    except Exception as e:
        bb.debug(1,"XML-Validation failed")
        bb.debug(1,"%s" % e)

    try:
        jsontree = JSON.loads(x)
        extList.append(".json")

    except Exception as e:
        bb.debug(1, "JSON-Validation failed")
        bb.debug(1, "%s" % e)

    if  ( 0 == len(extList) or
          1 >  len(extList)
        ):

        bb.error("Extension detection for DOM string failed! Extension set to \'.unknown\'! Reason: %s" % ("No extensions" if 0 == len(extList) else "Multiple extensions"))
        selectedExt = ".unknown"

    else:
        selectedExt = extList[0]

    return selectedExt

def xmlWellFormedCheck(file):
    from lxml import etree
    isWellFormed = True

    try:
        bb.debug(1, "Check for Well-formed XML of: %s" % file)
        dom = etree.parse(file)
    except:
        isWellFormed = False

    return isWellFormed

def collectXslTransformationFiles (d):

    xslFileList = []
    imageLicenseDir = os.path.join(d.getVar("THISDIR"), "license-transform")
    indexes = lambda fn, list: [ e for (y, e) in zip(list, range(len(list))) if os.path.splitext(os.path.basename(y))[0] == fn]


    bb.debug(1,"Collect license transformation files (xsl, xslt) from recipe image \'%s\' directory: %s" % (d.getVar("IMAGE_BASENAME"), imageLicenseDir))
    try:
        if os.path.isdir(imageLicenseDir) is True:
            for file in os.listdir(imageLicenseDir):
                f = os.path.splitext(os.path.basename(os.path.join(imageLicenseDir, file)))
                if ".xsl" == f[1] or ".xslt" == f[1]:
                    bb.debug(1, "Found xsl/xslt file")
                    bb.debug(1, "File to check is:          %s" % file)
                    bb.debug(1, "Listed files (basename):   %s" % [ os.path.splitext(os.path.basename(p))[0] for p in xslFileList])
                    if f[0] in [ os.path.splitext(os.path.basename(p))[0] for p in xslFileList]:
                        fileInList = os.path.basename(xslFileList[indexes(f[0],xslFileList)[0]])
                        raise Exception("Detected identical basenames for files in %s\nin list: %s <=> checked: %s\nIdentical basenames are not allowed!" % (imageLicenseDir, fileInList , file ))
                    else:
                        xslFileList.append(os.path.join(imageLicenseDir, file))

        bb.debug(1, "Found %s custom transformation files in %s" % (len(xslFileList), imageLicenseDir))
        cesBaseXSLFile = d.getVar("LIC_CES_TRANSFORM_FILE")
        if os.path.isfile ( cesBaseXSLFile) is not True:
            raise("Missing LIC_CES_TRANSFORM_FILE: %s" % cesBaseXSLFile)

        bb.debug(1, "Try to collect LIC_CES_TRANSFORM_FILE")
        cesBaseXSLName = os.path.splitext(os.path.basename(cesBaseXSLFile))[0]
        matches = indexes(cesBaseXSLName, xslFileList)
        if 0 != len(matches):
            raise Exception("Uniqueness error: LIC_CES_TRANSFORM_FILE %s conflicts with basename identical file: %s\n " % (cesBaseXSLFile, xslFileList[indexes(f[0],xslFileList)[0]]))

        xslFileList.append(cesBaseXSLFile)

    except Exception as exception:
        bb.fatal("%s" % exception)

    for file in xslFileList:
        if xmlWellFormedCheck(file) is not True:
            bb.fatal("Well-Formed check failed on: %s" % file)

    bb.debug(1, "Collected transformation files:\n%s" % "\t\n".join(xslFileList))

    return xslFileList

python do_license_xml_transform () {
    import lxml.etree as LET

    collected = collectXslTransformationFiles(d)

    xmlManifestFile = d.getVar("IMAGE_BASENAME") + '-manifest' + ".xml"
    outputBaseDir = os.path.join(d.getVar("WORKDIR"), "license-rootfs-manifest")
    licXmlManifestUri = os.path.join(outputBaseDir, xmlManifestFile)

    if os.path.isfile(licXmlManifestUri) is not True:
        bb.fatal("LIC_XML_MANIFEST_URI %s file not found!" %  licXmlManifestUri)

    if 0 != len(collected):
        for xslf in collected:
            bb.debug(1, "Try license manifest transformation with: %s" % xslf)
            dom = transformXMLviaXSLT(licXmlManifestUri, xslf)
            if None != dom:
                basename = os.path.splitext(os.path.basename(xslf))[0]
                ext = weakDomStringTypeCheck(str(LET.tostring(dom, encoding="unicode")))
                bb.debug(1, "Basename for transformed file:     %s" % basename)
                bb.debug(1, "Extension for transformed file:    %s" % ext)
                if ".unknwon" == ext:
                    bb.warn(".unknown extension set for: %s" % basename)
                with open (os.path.join(outputBaseDir, basename + ext), 'wb', -1) as output:
                    output.write(LET.tostring(dom, pretty_print=True))
}
do_license_xml_transform[deptask] = "do_license_xml_manifest"

def getPackageLicenseNode (rootdir):

    import sys, re, json, chardet
    from xml.etree.ElementTree import Element, SubElement, Comment, tostring
    from xml.etree import ElementTree
    from xml.dom import minidom

    packagelistNode = Element('Packagelist')

    if os.path.isdir(rootdir) is not True:
        bb.fatal("Yocto rootfs path is not valid! %s" % rootdir)
        return None

    list  = os.listdir(rootdir)
    sortedDirList = sorted(list, key=str.casefold)

    for dir in sortedDirList:
        f = None
        modPath= None
        pn = None
        lic = None
        pv = None
        lines = None
        genericLic = []
        nongenericDic = {}
        content = None
        dirEntry = os.path.join(rootdir, dir)

        if os.path.isdir(dirEntry) is not True:
            continue

        modPath=os.path.join(dirEntry, "recipeinfo")

        if os.path.isfile(modPath) is True:
            pn = dir
        else:
            continue

        for root, dirs, files in os.walk(dirEntry):
            for file in files:
                if 'recipeinfo' not in file:
                    if 'generic_' in file:
                        genericLic.append(file.split('generic_')[1])
                    else:
                        try:
                            with open(os.path.join(dirEntry,file),'rb') as lictext:
                                content = lictext.read()
                        except:
                            bb.error ('Exception on: %s' % pn )

                        nongenericDic[file] = content

        with  open(modPath, 'r') as f:
            lines = f.readlines()

        for line in lines:
            if re.match(re.compile("^LICENSE:.{1,}$"), line) is not None:
                lic = line.split(':')[1].strip()
            if re.match(re.compile("^PV:.{1,}$"),line) is not None:
                pv = line.split(':')[1].strip()

        f.close()

        package_node = Element('package')

        elpn = SubElement(package_node, 'Packagename')
        elpn.text =  pn

        elpv = SubElement(package_node, 'Version')
        elpv.text =  pv

        ellic = SubElement(package_node, 'YoctoReferencedLicenses')
        ellic.text =  lic

        genericNode = SubElement(package_node, 'genericLicenses')
        nongenericNode = SubElement(package_node, 'nongenericLicenses')

        for gen in genericLic:
            genericLicNode = SubElement(genericNode,'License')
            genericLicNode.text = gen

        for ng in nongenericDic:
            child = SubElement(nongenericNode,'License')
            SubElement(child,'File').text = ng

            result = chardet.detect(nongenericDic[ng])['encoding']
            lictext = nongenericDic[ng].decode(result)
            textnode = SubElement(child,'Text')

            lictext = re.sub('[\x09]', '        ' , lictext)
            lictext = re.sub('[\x00-\x08\x0b-\x1f\x7f-\x9f]', '' , lictext)

            splittedText = lictext.split("\n")
            textnode.text  = splittedText[0]

            for s in splittedText[1:]:
                br = SubElement(textnode, "br")
                br.tail = s

        packagelistNode.append(package_node)

    return packagelistNode

def getNPMModuleLicenseNode(rootfs):

    import sys, re, json, chardet
    from xml.etree.ElementTree import Element, SubElement, Comment, tostring
    from xml.etree import ElementTree
    from xml.dom import minidom
    from pathlib import Path
    import subprocess
    import hashlib

    requiredVersion = "25.0.1"

    if os.path.isdir (rootfs) is not True:
        bb.fatal("NPM license module scan failed! Directory doesn't exist!\n Dir: %s" % rootfs)
        return None

    resultVersion = subprocess.run(["license-checker", "--version"], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    resultVerStr = resultVersion.stdout.decode('utf-8').rstrip("\n")
    if requiredVersion != resultVerStr:
        bb.warn("Untested license-checker version detected! Version: %s Required: %s" % (resultVerStr, requiredVersion))

    moduleList = Element('NpmModuleList')
    moduleListHashes = []
    modulesDoubleCnt = 0
    modulesConflictCnt = 0
    modulesCnt = 0

    for path in Path(rootfs).rglob('package.json'):
        dir = os.path.dirname(str(path))
        nmdir = os.path.join(dir, "node_modules")

        if os.path.isdir(nmdir):
            result = subprocess.run(["license-checker","--start",  dir,"--unknown", "--production", "--excludePrivatePackages", "--json"], stdout=subprocess.PIPE)
            try:
                modulesJson = json.loads(result.stdout.decode('utf-8'))
                for module in modulesJson:

                    moduleNode = Element('NpmModule')
                    mpn = SubElement(moduleNode, 'Modulename')
                    mv = SubElement(moduleNode, 'Version')
                    mlic = SubElement(moduleNode, 'Modulelicenses')
                    licenseInfo = Element('License')

                    moduleStr = module.split('@')
                    moduleName = ""
                    moduleVersion = ""

                    if 1 > len(moduleStr):
                        raise Exception("NPM license-checker returned invalid name/version string format: %s" % moduleStr)


                    modulesCnt += 1
                    for i in range(len(moduleStr) - 1):
                        moduleName = moduleName + moduleStr[i]

                    moduleVersion = moduleStr[len(moduleStr) - 1 ]

                    mpn.text = moduleName
                    mv.text = moduleVersion

                    modLic = modulesJson[str(module)]["licenses"]

                    if isinstance(modLic, list):
                        modLics = modLic
                        modLic = None
                        for e in modLics:
                            if modLic == None:
                                modLic = e
                            else:
                                modLic = modLic + " ; " + e

                    mlic.text = modLic

                    with open(modulesJson[str(module)]["licenseFile"],'rb') as rawlictext:
                        content = rawlictext.read()

                    result = chardet.detect(content)['encoding']
                    lictext = content.decode(result)

                    h = hashlib.sha256()
                    h.update(moduleVersion.encode('utf-8'))
                    hashVersionSha256 = h.hexdigest()
                    del h

                    h = hashlib.sha256()
                    h.update(modLic.encode('utf-8'))
                    hashLicenseListSha256 = h.hexdigest()
                    del h

                    h = hashlib.sha256()
                    h.update(lictext.encode('utf-8'))
                    hashLicenseTextSha256 = h.hexdigest()
                    del h

                    hashModuleObject =  {
                                            "moduleName":       moduleName,
                                            "hashVersion":      hashVersionSha256,
                                            "hashLicenseList":  hashLicenseListSha256,
                                            "hashLicenseText":  hashLicenseTextSha256,
                                        }

                    resultModuleList = []
                    for obj in moduleListHashes:
                        if (obj['moduleName'] == moduleName):
                            resultModuleList.append(obj)

                    addObjectToManifest = True
                    hasHashConflict = True
                    if 0 != len(resultModuleList):
                        for existingModule in resultModuleList:
                            bb.debug(1, "Hash-Check: Module: %s" % moduleName)
                            bb.debug(2, "Existing version:          %s\t\tChecked version:         %s " % (existingModule["hashVersion"]        , hashModuleObject["hashVersion"]))
                            bb.debug(2, "Existing licenses:         %s\t\tChecked licenses:        %s " % (existingModule["hashLicenseList"]    , hashModuleObject["hashLicenseList"]))
                            bb.debug(2, "Existing license text:     %s\t\tChecked license text:    %s " % (existingModule["hashLicenseText"]    , hashModuleObject["hashLicenseText"]))

                            if (
                                existingModule["hashVersion"]     == hashModuleObject["hashVersion"]     and
                                existingModule["hashLicenseList"] == hashModuleObject["hashLicenseList"] and
                                existingModule["hashLicenseText"] == hashModuleObject["hashLicenseText"]
                               ):

                                bb.debug(1, "Detected identical NPM module: %s !" % moduleName)
                                addObjectToManifest = False
                                modulesDoubleCnt += 1
                                break

                    if True == addObjectToManifest:

                        moduleListHashes.append(hashModuleObject)

                        if 0 != len(resultModuleList):
                            bb.debug(1, "Detected hash conflicting/multiple version of NPM module: %s" % moduleName)
                            bb.debug(1, "Detected %s name identical modules" % len(resultModuleList))
                            modulesConflictCnt += 1

                        lfn = SubElement(licenseInfo,"Licensefile")
                        lfn.text = os.path.basename(modulesJson[str(module)]["licenseFile"] )

                        ltn = SubElement(licenseInfo,"Licensetext")

                        lictext = re.sub('[\x09]', '        ' , lictext)
                        lictext = re.sub('[\x00-\x08\x0b-\x1f\x7f-\x9f]', '' , lictext)

                        splittedText = lictext.split("\n")
                        ltn.text  = splittedText[0]

                        for s in splittedText[1:]:
                            br = SubElement(ltn, "br")
                            br.tail = s

                        moduleNode.append(licenseInfo)
                        moduleList.append(moduleNode)

            except ValueError as e:
                bb.fatal("license-checker: result seems not to be a valid json")

    if 0 != modulesConflictCnt: bb.warn("Conflicting/Multiple NPM modules:  %s" % modulesConflictCnt)
    if 0 != modulesDoubleCnt:   bb.warn("Identical NPM modules:             %s" % modulesDoubleCnt)
    bb.debug(1, "Detected NPM modules total:     %s" % modulesCnt)

    sortedList = sorted ( moduleList.findall("NpmModule") , key = lambda x: x.find("Modulename").text.casefold())
    moduleList[:] = sortedList

    return moduleList if 0 != len(moduleList) else  None

def getGenericLicenseXMLNode(path):

    import sys, re, json, chardet
    from xml.etree.ElementTree import Element, SubElement, Comment, tostring
    from xml.etree import ElementTree
    from xml.dom import minidom

    generic_node = Element('GenericLicenses')
    list = os.listdir(path)
    sortedList = sorted(list, key=str.casefold)

    for file in sortedList:
        if 'generic_' in file:
            with open(os.path.join(path,file),'rb') as lictext:
                content = lictext.read()

            result = chardet.detect(content)['encoding']
            lictext = content.decode(result)

            child = Element('License')
            SubElement(child,'Name').text = file.split('generic_')[1]
            genericLicTextNode = SubElement(child,'Text')

            lictext = re.sub('[\x09]', '       ' , lictext)
            lictext = re.sub('[\x00-\x08\x0b-\x1f\x7f-\x9f]', '' , lictext)

            splittedText = lictext.split("\n")
            genericLicTextNode.text  = splittedText[0]

            for s in splittedText[1:]:
                br = SubElement(genericLicTextNode, "br")
                br.tail = s

            generic_node.append(child)

    return generic_node

def getbasicMachineXMLNode(d):

    from xml.etree.ElementTree import Element, SubElement, Comment, tostring
    import datetime

    basicNode = Element('GeneralInformation')

    imageBasename = d.getVar("IMAGE_BASENAME")
    imageVersion = d.getVar("PV")
    imageRecipeRevision = d.getVar("PR")
    imageDate = d.getVar("DATE")
    machine = d.getVar("MACHINE")
    license = d.getVar("LICENSE")
    copyrightYear = str(datetime.datetime.now().year)
    distro = d.getVar("DISTRO")
    distroCodename = d.getVar("DISTRO_CODENAME")
    distroVersion = d.getVar("DISTRO_VERSION")

    imgNameNode = SubElement(basicNode, 'Imagename')
    imgNameNode.text = imageBasename

    imgVerNode = SubElement(basicNode, 'Imageversion')
    imgVerNode.text = imageVersion

    imgRecVerNode = SubElement(basicNode, 'ImageRecipeRevision')
    imgRecVerNode.text = imageRecipeRevision

    imgGenDate = SubElement(basicNode, 'DateOfGeneration')
    imgGenDate.text = imageDate[0:4] + '-' + imageDate[4:6] + '-' + imageDate[6:8]

    machineNode = SubElement(basicNode, 'Machine')
    machineNode.text = machine

    licenseNode = SubElement(basicNode, 'Imagelicense')
    licenseNode.text = license

    copyrightNode = SubElement(basicNode, 'Copyrightyear')
    copyrightNode.text = copyrightYear

    distroNode = SubElement(basicNode, 'Distro')
    distroNode.text = distro

    distroVersionNode = SubElement(basicNode, 'Distroversion')
    distroVersionNode.text = distroVersion

    distroCodenameNode = SubElement(basicNode, 'Distrocodename')
    distroCodenameNode.text = distroCodename

    return basicNode



fakeroot python do_license_xml_manifest () {
    from xml.etree.ElementTree import Element
    import lxml.etree as LET
    import shutil

    copy_lic_manifest = d.getVar('COPY_LIC_MANIFEST')
    copy_lic_dirs = d.getVar('COPY_LIC_DIRS')

    if ("1" !=  copy_lic_manifest) or ("1" !=  copy_lic_dirs):
        bb.fatal( "Task do_licenseXMLmanifest aborted\n" +
                 "COPY_LIC_MANIFEST and COPY_LIC_DIRS options not set in environment")

    docnode = Element('YoctoLicenseRoot')

    try:
        workdir = d.getVar("WORKDIR")
        imagename = d.getVar("IMAGE_BASENAME")
        outputBaseDir = d.getVar("LIC_IMAGE_MANIFEST_DIR")
        licensedir = os.path.join(d.getVar('IMAGE_ROOTFS'),'usr', 'share', 'common-licenses')
        outputBaseName = imagename + '-manifest'

        bb.debug(1, "Generate license xml manifest from rootfs source")
        bb.debug(1, "Workdir:      %s" % workdir )
        bb.debug(1, "Licensedir:   %s" % licensedir )
        bb.debug(1, "Imagename:    %s" % imagename )

        if os.path.isdir(licensedir) is not True:
            raise Exception('License root directory is not available')

        genericLicenseNode = getGenericLicenseXMLNode(licensedir)
        basicNode = getbasicMachineXMLNode(d)
        packageListNode = getPackageLicenseNode(licensedir)
        npmModulesList = getNPMModuleLicenseNode(d.getVar('IMAGE_ROOTFS'))

        docnode.append(basicNode)
        if None != genericLicenseNode:  docnode.append(genericLicenseNode)
        if None != packageListNode: docnode.append(packageListNode)
        if None != npmModulesList:  docnode.append(npmModulesList)

        if os.path.exists (outputBaseDir) is not True:
            bb.warn("LIC_IMAGE_MANIFEST_DIR: %s does not exist! Create directory!" % outputBaseDir)
            os.mkdir (outputBaseDir)

        os.path.join(outputBaseDir, outputBaseName + '.xml')

        with open (os.path.join(outputBaseDir, outputBaseName + '.xml'), 'wb', -1) as output:
            output.write(createPrettifiedXMLDoc(docnode))

    except Exception as inst:
        bb.error ("%s" % inst)
}
do_license_xml_manifest[nostamp] = "1"
do_license_xml_manifest[cleandirs] =  "${WORKDIR}/license-rootfs-manifest"


python do_license_manifest_deploy () {
    from xml.etree.ElementTree import Element
    import lxml.etree as LET
    import shutil
    import re

    filesByExtensions = []
    filesByFilenames = []
    listByExtensions = []
    listByFilenames = []

    if None != d.getVar("LIC_DEPLOY_BY_EXTENSION"):
        if d.getVar("LIC_DEPLOY_BY_EXTENSION"):
            listByExtensions = re.sub("\s+"," ",(d.getVar("LIC_DEPLOY_BY_EXTENSION"))).strip().split(" ")
            if len(listByExtensions):
                for e in listByExtensions:
                    if None == re.match("^\.([a-zA-Z0-9\s_\\\.\-\(\):])+$", e):
                        bb.fatal("LIC_DEPLOY_BY_EXTENSION entry not valid: \'%s\'" % e)

    if None != d.getVar("LIC_DEPLOY_BY_FILENAME"):
        if d.getVar("LIC_DEPLOY_BY_FILENAME"):
            listByFilenames = re.sub("\s+", " ",(d.getVar("LIC_DEPLOY_BY_FILENAME"))).strip().split(" ")

    bb.debug(1, "LIC_DEPLOY_BY_EXTENSION: %s" % listByExtensions)
    bb.debug(1, "LIC_DEPLOY_BY_FILENAME: %s" % listByFilenames)

    if ( 0 == len(listByExtensions) and
         0 == len(listByFilenames)
       ):
        bb.fatal("Either use LIC_DEPLOY_BY_FILENAME or LIC_DEPLOY_BY_EXTENSION or both for license file deployment to image\n Empty variables catched")

    if False == os.path.isdir(d.getVar("LIC_IMAGE_MANIFEST_DIR")):
        bb.fatal("LIC_IMAGE_MANIFEST_DIR %s does not exist" % d.getVar("LIC_IMAGE_MANIFEST_DIR"))

    listManifestDir = os.listdir(d.getVar("LIC_IMAGE_MANIFEST_DIR"))

    for file in listManifestDir:
        for ext in listByExtensions:
            if file.endswith(ext):
                 filesByExtensions.append(os.path.join(d.getVar("LIC_IMAGE_MANIFEST_DIR"), file))

    for file in listByFilenames:
        if file in listManifestDir:
            filesByFilenames.append(os.path.join(d.getVar("LIC_IMAGE_MANIFEST_DIR"),file))
        else:
            bb.fatal("Deployerror: File \'%s\' not found in %s" % (file, d.getVar("LIC_IMAGE_MANIFEST_DIR")))

    bb.debug(1, "List collected by filenames:   \n%s" % "\n".join(filesByFilenames))
    bb.debug(1, "List collected by extensions:  \n%s" % "\n".join(filesByExtensions))

    filesByBoth = list(set(filesByFilenames + filesByExtensions))
    bb.debug(1, "Deploylist: %s\n" % "\n".join(filesByBoth))

    targetDir = d.getVar("LIC_IMAGE_DEPLOY_DIR")
    if None == targetDir:
        bb.fatal("LIC_IMAGE_DEPLOY_DIR not set!")

    if os.path.isdir (targetDir):
        try:
            shutil.rmtree(targetDir)
        except Exception as e:
            bb.fatal("Remove directory %s from rootfs image deploy directory failed!\n %s" % (targetDir,e ))

    try:
        os.makedirs(targetDir)
    except FileExistsError:
        bb.warn("Deploydir %s already exists" % targetDir)
    except Exception as e:
        bb.fatal("mkdir of deploy directory for licenses failed!\nReason:\n %s" % e)

    for file in filesByBoth:
        try:
            shutil.copy2 (file, targetDir)
        except Exception as e:
            bb.fatal("Copy file \'%s\' to rootfs deploy directory %s failed!\nReason:\n %s" % (file, targetDir, e))

}
do_license_manifest_deploy[deptask] = "do_license_xml_manifest"

def nativepython_html_to_pdf(d, input, output):
    import subprocess

    weasyprintPath = os.path.join(d.getVar("STAGING_DIR_NATIVE", True), "usr","bin","weasyprint")

    try:
        weasyprint = subprocess.run([
                                        "nativepython3",
                                        weasyprintPath,
                                        "-f",
                                        "pdf",
                                        "-v",
                                        input,
                                        output
                                    ],
                                    stdout=subprocess.PIPE,
                                    stderr=subprocess.STDOUT
                                   )

        if weasyprint.returncode:
            raise Exception("weasyprint returned with non-zero exit code! Return: %s\nError: %s" %
                            (weasyprint.returncode, weasyprint.stdout.decode('utf-8')))

        bb.debug(1, "%s",  weasyprint.stdout.decode('utf-8'))

    except Exception as e:
        bb.fatal("nativepython weasyprint task failed!\n%s" % e)


python do_license_html_to_pdf () {

    nativeHtmlToPdf = False
    try:
        from weasyprint import HTML, CSS
    except ImportError:
        nativeHtmlToPdf = True
        bb.warn("Host system: Python: Missing module \'weasyprint\'! Try to use it with native python in sysroot!")
        bb.warn("Use pip3 install weasyprint to make \'weasyprint\' available on host system")

    outputBaseDir = d.getVar("LIC_IMAGE_MANIFEST_DIR")

    if os.path.isdir(outputBaseDir) is not True:
        bb.error("Output directory %s is missing" % outputBaseDir)
        return

    files = os.listdir(outputBaseDir)

    for file in files:
        r = os.path.splitext(file)
        if ".html" == r[1]:
            bb.debug(1, "Found html file %s in %s! Try html to pdf conversion" % (file, outputBaseDir))
            if nativeHtmlToPdf:
                nativepython_html_to_pdf(d, os.path.join(outputBaseDir, file), os.path.join(outputBaseDir, r[0] + ".pdf"))
            else:
                HTML(filename = os.path.join(outputBaseDir, file)).write_pdf(os.path.join(outputBaseDir, r[0] + ".pdf"))
}
do_license_html_to_pdf[deptask] = "do_license_xml_transform"

python () {

    d.setVar("LIC_IMAGE_MANIFEST_DIR",os.path.join(d.getVar("WORKDIR"), "license-rootfs-manifest"))
    d.setVar("LIC_IMAGE_DEPLOY_DIR",  os.path.join(d.getVar('IMAGE_ROOTFS'),'usr', 'share', 'common-licenses','license-rootfs-manifest'))

    #@TEST ONLY: That piece of code is a quite nasty idea of how to bypass the host/native python implementation of modules
    #            Host python (instance is running bitbake) and native python (compiled on runtime) are using different python implementations,
    #            this funny piece of code allows the use of isolated module for native python in host python.
    #            ONLY USE THIS PIECE OF CODE, IF YOU KNOW WHAT YOU DO!
    #            It's possible to break your system completley. Just a missmatch in the used python versions could lead to unexpected beahviour
    #
    #            Inheritance of nativepython3 in this class is a not a welcome approach, because it could break the class for the same reasons, like
    #            written before.
    #
    #           The best approach would be, to seperate the implementation of license code to a completley different class, without inheritance
    #sys.path.append(os.path.join(d.getVar("STAGING_DIR_NATIVE", True), "usr","lib","python3.7","site-packages"))

    if "1" != d.getVar("LIC_XML_MANIFEST", True):
        d.setVarFlag("do_license_xml_manifest"   , 'noexec', "1")
        d.setVarFlag("do_license_xml_transform"  , 'noexec', "1")
        d.setVarFlag("do_license_manifest_deploy", 'noexec', "1")
        if "1" != d.getVar("LIC_DISABLE_HTML_TO_PDF"):
            d.setVarFlag("do_license_html_to_pdf"    , 'noexec', "1")

}

DEPENDS += " \
        python3-native \
        license-checker-native \
	    python3-weasyprint-native \
	    python3-lxml-native \
"

addtask license_xml_manifest after do_image before do_license_xml_transform
addtask do_license_xml_transform after do_license_xml_manifest before do_license_html_to_pdf
addtask license_html_to_pdf after do_license_xml_transform before do_license_manifest_deploy
addtask license_manifest_deploy after do_license_html_to_pdf before do_image_tar
