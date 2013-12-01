#!/bin/sh
param=$1
serverurl=$2
wikidumpFile="enwikinews-20131115-pages-meta-current.xml.bz2"
wikidumpxmlFile="enwikinews-20131115-pages-meta-current.xml"
wikidumpFileLink="http://dumps.wikimedia.org/enwikinews/20131115/enwikinews-20131115-pages-meta-current.xml.bz2"
wikiDumpExtractedFolder="extracted"

if [ $# -ne 2 ];
then
	echo "incorrect no of parameters. --serverurl http://solr_url/core"
	exit 2
fi

#echo $param
#echo $serverurl

if [ "$param"="--serverurl" ];
then
	echo "server url found"
else
	echo "wrong parameter"
	exit 2
fi

if [ -d $wikiDumpExtractedFolder ];
then
	echo "extracted folder already exists"
	echo "reading wiki documents from $wikiDumpExtractedFolder..."
elif [ -f $wikidumpFile ];
then
	echo "extracting..."
	bzcat $wikidumpFile | python WikiExtractor.py -o $wikiDumpExtractedFolder
fi
java -jar newsindexer.jar --server $serverurl --dir $wikiDumpExtractedFolder
