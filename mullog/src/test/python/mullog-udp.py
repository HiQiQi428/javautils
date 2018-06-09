
from socket import AF_INET, SOCK_DGRAM, socket
import select

udp = socket(AF_INET, SOCK_DGRAM)
udp.bind(('0.0.0.0', 16000))
try:
    while select.select([udp], [], [])[0]:
        print udp.recvfrom(10000)[0]
except KeyboardInterrupt, e:
    print '. Quit'
