#!/usr/bin/env python
import re
import sys
import time
import urllib2
import hashlib
import os
import random
import Queue
import threading
 
#settings
pages = 10 # number pages to download( 1 page = ~50 samples)
dldfolder = "C:\malware\\"
info = "_files.txt"
errors = "_errors.txt"
malware_url = "_mal_url.txt"
useragent = { 'User-Agent' : 'Malc0de.com samples downloader v3.0 http://ViRii.Tk'}
 
print """
Malc0de.com samples downloader v3.0
               )\._.,--....,'``.       
  .b--.        /;   _.. \   _\  (`._ ,. 
 `=,-,-'~~~   `----(,_..'--(,_..'`-.;.'
http://virii.tk    http://twitter.com/ViRiiTk
"""
 
#create download folder if not exist
if not os.path.isdir(dldfolder):
    os.mkdir(dldfolder)
 
q = Queue.Queue()
 
#generate random string
def get_random_word(a):
    word = ''
    for i in range(a):
        word += random.choice('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789')
    return word
 
#md5 file
def md5Checksum(filePath):
    fh = open(filePath, 'rb')
    m = hashlib.md5()
    while True:
        data = fh.read(8192)
        if not data:
            break
        m.update(data)
    return m.hexdigest()
 
#find all malware address on curent page
def getmalware(pagina): 
    b = re.findall("<td>[\d]{4}-[\d]{2}-[\d]{2}<\/td>\n.+\n", pagina)
    if b:
        for i in b:
            data = re.search("<td>([\d]{4}-[\d]{2}-[\d]{2})<\/td>", i)
            malware = re.search("\t<td>(.+)<\/td>", i)
            if data and malware:
                malware= re.sub("<br\/>", "",malware.group(1) )
                #print data.group(1), malware
                q.put(data.group(1)+ "._." + malware)
                 
#browsing pages
print "Browsing pages:"
for i in range(1, pages +1):
    adresa = "http://malc0de.com/database/?&page=" + str(i)
    print "Searching on:", adresa
    time.sleep(10) # pauza intre pagini (s)
    try:
        req = urllib2.Request(adresa, None, useragent)
        response = urllib2.urlopen(req)
        continut = response.read()
        getmalware(continut)
    except Exception as e:
        print e
        pass
 
#download malware
def dld_mal(url_mal):
    mal_data = url_mal.split("._.")[0]
    url_mal = url_mal.split("._.")[1]
    with open(dldfolder + malware_url, "a") as handle:
        handle.write(mal_data + "\t" + url_mal + "\n")
    file_name = url_mal.split("/")[-1]
    if re.search("\?", file_name) or re.search("\&", file_name):
        file_name = "No_real_name-" + get_random_word(8)
    try:
        if url_mal[:7] != "http://":
            url_mal = "http://" + url_mal
        u = urllib2.urlopen(url_mal, timeout = 50) #timeout 1 min
        f_name = dldfolder + str(file_name) +"_" + get_random_word(3) # every downloaded malware will have a uniq name: "Malware_sample" + "_" + 3 random characters
        f = open(f_name, 'wb')
        block_sz = 8192
        while True:
            buffer = u.read(block_sz)
            if not buffer:
                break
            f.write(buffer)
        f.close()
        with open(dldfolder + info, "a") as handle:
            md5hash = md5Checksum(f_name)
            handle.write(str(mal_data) + "\t" +str(md5Checksum(f_name)) +"\t" + str(file_name)+"\n")
        print file_name
    except Exception as e:
        with open(dldfolder + errors, "a") as handle:
            handle.write(str(mal_data) + "\t" + url_mal + "\t" + str(e) + "\n")
        #print "Can't download this:", url_mal, e
        pass
 
print "Downloading:"
while True:
   if not q.empty():
       item = q.get()
       #dld_mal(item)
       worker = threading.Thread(target=dld_mal, args=(item,))
       worker.start()
       q.task_done()
   else:
        sys.exit() 
q.join()