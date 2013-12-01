#!/bin/sh
param=$1
serverurl="http://localhost:8983/solr/newsx"
wikidumpFile="enwikinews-20131115-pages-meta-current.xml.bz2"
wikidumpxmlFile="enwikinews-20131115-pages-meta-current.xml"
wikidumpFileLink="http://dumps.wikimedia.org/enwikinews/20131115/enwikinews-20131115-pages-meta-current.xml.bz2"
wikiDumpExtractedFolder="extracted"

if [ $# -ne 2 ];
then
	echo "...incorrect no of parameters. -N no_of_docs_to_index"
	exit 2
fi

#echo $param
#echo $serverurl

#if [ "$param"="-S" ];
#then
#	echo "server url found"
#else
#	echo "wrong parameter"
#	exit 2
#fi

if [ -d $wikiDumpExtractedFolder ];
then
	echo "...extracted folder already exists...N will be ignored"
	echo "...delete the folder:$wikiDumpExtractedFolder if you want to enforce N "
	echo "...reading wiki documents from $wikiDumpExtractedFolder..."
elif [ -f $wikidumpFile ];
then
	echo "...extracting....."
	bzcat $wikidumpFile | python WikiExtractor.py -o "$wikiDumpExtractedFolder" -N "$2"
else
	wget $wikidumpFileLink
	echo "...extracting..."
	bzcat $wikidumpFile | python WikiExtractor.py -o "$wikiDumpExtractedFolder" -N "$2"
fi
java -jar newsindexer.jar --server $serverurl --dir $wikiDumpExtractedFolder
