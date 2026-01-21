
import json

import row

from acnh.models import *

file =json.loads('archivo.json')



for r in file_rows:
    v = Villager()
    v.name=  r["village_name"]
    v.personality = r["personality"]
    v.imageUrl = "https://raw.githubusercontent.com/alexislours/ACNHAPI/refs/heads/master/images/villagers/" + row["file_name"]+ ".png"

#https://github.com/alexislours/ACNHAPI/blob/master/v1a/villagers.json


