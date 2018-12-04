# babelnetpy
A python 3 interface for BabelNet https://babelnet.org/
This is the extension of HTTP API https://babelnet.org/guide

# install
Please download the project, use cd to move to the pybabelfy folder and run:
```
python setup.py install
```

# Getting started
Please register BabelNet (https://babelnet.org/register) and get your API key.
Example codes is in examples directory.
 ```
from pybabelnet.babelnet import BabelNet
bn = BabelNet(open("key.txt", "r").read()) # or BabelNet("your API key")
synsets = bn.getSynsets("bn:03083790n")
print(synsets[0].senses)
#getSenses is able to use after only getSynsets
senses = bn.getSenses("BabelNet", "EN")
print(senses[0])
#getSynsetIdsFromResourceID is able to use after only getSynsets
synset_ids = bn.getSynsetIdsFromResourceID("BabelNet", "EN", "NOUN", "WIKI")
print(synset_ids[0])
```

# infomation
HTTP API has more function than this code has; however it is needed to accesss several times. To reduce times and avoid using babelcoins, I don't make their functions. Perhaps, their functions may help us to search Babelnet, but I don't know that whether I add them or not.

## Lang
'''
EN,
ES,
IT,
etc...
'''

## pos
'''
NOUN,
ADJ,
VERB,
ADV,
INTJ,
DET,
CCONJ,
PRON,
etc...
'''

## source
'''
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
'''
