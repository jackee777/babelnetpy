# pybabelnet
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
synset_ids = \
    bn.getSynsetIdsFromResourceID("BabelNet", "EN", "NOUN", "WIKI")
print(synset_ids[0])
```
