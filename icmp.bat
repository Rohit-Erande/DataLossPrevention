
"C:\Program Files\Wireshark\editcap.exe" -c 10 -F k12text %1 C:\Users\Poojitha\Desktop\Attack\Packet
"C:\yara\yara64.exe" -r  C:\yara\my-rule-file.yar C:\Users\Poojitha\Desktop\Attack >  C:\Users\Poojitha\Downloads\DLP\DataLossPrevention-master\yara-output.txt