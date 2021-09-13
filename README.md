# babelnetpy
A python 3 interface for BabelNet https://babelnet.org/
This is the extension of HTTP API https://babelnet.org/guide

# How to install
Please download the project, use cd to move to the babelnetpy folder and run:
```
python setup.py install
```

# Getting started
Please register BabelNet (https://babelnet.org/register) and get your API key.
Example codes is in examples directory.
```
from babelnetpy.babelnet import BabelNet
bn = BabelNet("your API key") # or BabelNet(open("key.txt", "r").read())
synsets = bn.getSynsets("bn:03083790n")
print(synsets[0].senses)
#getSenses is able to use after only getSynsets
senses = bn.getSenses("BabelNet", "EN")
print(senses[0])
#getSynsetIdsFromResourceID is able to use after only getSynsets
synset_ids = bn.getSynsetIdsFromResourceID("BabelNet", "EN", "NOUN", "WIKI")
print(synset_ids[0])
```

## About key.txt
The purpose of "key.txt" used in `BabelNet(open("key.txt", "r").read())` is to avoid a mistake that you update your API key accidentaly.
If you don't use the github or another web application, I recommend to use `BabelNet("your API key")` because you don't need to make an  additional file.
The "key.txt" in the example code means that you need to make "key.txt" and write your API key in that file. The API key is written on the home page after log-in.

# Caution about BabelNet's version update (HOWEVER, YOU SHOULD CHANGE IT)
In Apr/2021, BabelNet updates to version 6; however, this program does not change because I don't debug it.
If you want to use the new version, you write 'bn.API_PATH = "https://babelnet.io/v6/"' after 'bn = BabelNet("your API key")'.
(If anyone debug it and this program works correctly, please tell me (send the comment to [issue 6](https://github.com/jackee777/babelnetpy/issues/6)) / pull requests.)
Of course, self.API_PATH in line 15 of babelnet.py is same.

# Offline use
You need to set https://github.com/jackee777/babelnet_offline and run this server.
```
from babelnetpy.babelnet import BabelNet

bn = BabelNet(None)
bn.API_PATH = "http://localhost:1000/"
```
As far as I know, this server works well. However, I'm not familiar with the programming of the server. If you have trouble with the server, "babelnetlookup" is good and I sometimes maintenance it (unfortunately, babelnetpy cannot use against babelnetlookup).

# Infomation
HTTP API has more functions than this code has; however, it is needed to access several times. To reduce times and avoid using babelcoins, I don't make their functions. Perhaps, their functions may help us to search Babelnet, but I don't know whether I add them or not.

## Lang
```
EN,
ES,
IT,
CA,DE,FR,IS,PL,RO,AF,AR,BG,CS,CY,DA,EL,ET,FA,FI,GA,HE,HI,HR,HU,ID,JA,KO,LT,LV,MS,NL,NO,PT,RU,SK,SL,SQ,SR,SV,SW,TL,TR,UK,VI,ZH,MT,EU,EO,LA,GL,WAR,CEB,MIN,KK,UZ,HY,VO,NN,AZ,TH,OC,KA,MK,BE,NEW,TT,PMS,TA,TE,HT,UR,BS,BR,JV,MG,CE,LB,MR,IS,ML,PNB,BA,MY,LMO,BN,YO,FY,AN,CV,TG,KY,NE,IO,GU,BPY,SCO,SCN,NDS,KU,AST,QU,SU,ALS,GD,KN,AM,IA,NAP,CKB,BUG,WA,MN,ARZ,MZN,SI,PA,YI,SAH,VEC,FO,SA,BAR,NAH,OS,PAM,OR,HSB,SE,LI,MRJ,MI,ILO,CO,HIF,BCL,GAN,FRR,BO,RUE,GLK,MHR,PS,TK,PAG,VLS,GV,XMF,DIQ,KM,KV,ZEA,CSB,CRH,HAK,VEP,AY,DV,SO,SC,NRM,RM,UDM,KOI,KW,UG,STQ,LAD,WUU,LIJ,FUR,EML,MT,AS,BH,GN,PI,GAG,PCD,KSH,NOV,SZL,ANG,NV,IE,ACE,EXT,FRP,MWL,LN,SN,DSB,LEZ,PFL,KRC,HAW,PDC,KAB,XAL,RW,MYV,TO,ARC,KL,BJN,KBD,LO,HA,PAP,TPI,AV,LBE,MDF,JBO,WO,NA,BXR,TY,SRN,IG,NSO,KG,TET,KAA,AB,LTG,ZU,ZA,TYV,CDO,CHY,RMY,CU,TN,CHR,TW,GOT,BI,PIH,SM,RN,BM,MO,SS,IU,SD,PNT,KI,OM,XH,TS,EE,AK,FJ,TI,KS,LG,SG,NY,FF,VE,CR,ST,DZ,TUM,CH,SIMPLE,MUL,BE_X_OLD,NDS_NL,CBK_ZAM,ROA_RUP,FIU_VRO,BAT_SMG,IK,SH,AZB,MAI,LRC,GOM,OLO,JAM,TCY,ADY,ZH_CLASSICAL,ZH_YUE,ZH_MIN_NAN
```

## PoS (Part of Speech)
```
NOUN,
ADJ,
VERB,
ADV,
INTJ,
DET,
CCONJ,
PRON,
etc...
```

## Source
```
BABELNET,	// BabelNet senses, not available as of version 3.0
WN,		// WordNet senses
OMWN,		// Open Multilingual WordNet (deprecate)
IWN,		// Italian  WordNet
WONEF,		// WordNet du Francais
WIKI,		// Wikipedia page
WIKIDIS,	// Wikipedia disambiguation pages
WIKIDATA,	// Wikidata senses
OMWIKI,		// OmegaWiki senses
WIKICAT,	// Wikipedia category, not available as of version 3.0
WIKIRED,	// Wikipedia redirections
WIKT,		// Wiktionary senses
WIKIQU,		// Wikiquote page
WIKIQUREDI,	// Wikiquote redirections
WIKTLB,		// Wiktionary translation label
VERBNET,	// VerbNet senses
FRAMENET,	// FrameNet senses
MSTERM,		// Microsoft Terminology items
GEONM,		// GeoNames items
WNTR,		// Translations of WordNet senses
WIKITR,		// Translations of Wikipedia links
MCR_EU,		// Open Multilingual WordNet (Basque)
OMWN_HR,	// Open Multilingual WordNet (Croatian)
SLOWNET,	// Open Multilingual WordNet (Slovenian)
OMWN_ID,	// Open Multilingual WordNet (Indonesian)
OMWN_IT,	// Open Multilingual WordNet (Italian)
MCR_GL,		// Open Multilingual WordNet (Galician)
ICEWN,		// Open Multilingual WordNet (Icelandic)
OMWN_ZH,	// Open Multilingual WordNet (Chinese)
OMWN_NO,	// Open Multilingual WordNet (Norwegian (Bokmï¿½l))
OMWN_NN,	// Open Multilingual WordNet (Norwegian (Nynorsk))
SALDO,		// Open Multilingual WordNet (Swedish)
OMWN_JA,	// Open Multilingual WordNet (Japanese)
MCR_CA,		// Open Multilingual WordNet (Catalan)
OMWN_PT,	// Open Multilingual WordNet (Portuguese)
OMWN_FI,	// Open Multilingual WordNet (Finnish)
OMWN_PL,	// Open Multilingual WordNet (Polish)
OMWN_TH,	// Open Multilingual WordNet (Thai)
OMWN_SK,	// Open Multilingual WordNet (Slovak)
OMWN_LT,	// Open Multilingual WordNet (Lithuanian)
OMWN_NL,	// Open Multilingual WordNet (Dutch)
OMWN_AR,	// Open Multilingual WordNet (Arabic)
OMWN_FA,	// Open Multilingual WordNet (Persian)
OMWN_EL,	// Open Multilingual WordNet (Greek)
MCR_ES,		// Open Multilingual WordNet (Spanish)
OMWN_RO,	// Open Multilingual WordNet (Romanian)
OMWN_SQ,	// Open Multilingual WordNet (Albanian)
OMWN_DA,	// Open Multilingual WordNet (Danish)
OMWN_FR,	// Open Multilingual WordNet (French)
OMWN_MS,	// Open Multilingual WordNet (Malay)
OMWN_BG,	// Open Multilingual WordNet (Bulgarian)
OMWN_HE,	// Open Multilingual WordNet (Hebrew)
OMWN_KO,	// Korean WordNet
MCR_PT,		// Open Multilingual WordNet (Portuguese)
OMWN_GAE,	// Irish WordNet (GAWN)
WORD_ATLAS	// WordAtlas
```

# Thank you for reading
If this program is helpful for you, I want you to give the star this program for me because it leads to assist looking for my job. Have a nice day.
