from pybabelnet.babelnet import BabelNet

bn = BabelNet(open("key.txt", "r").read())

synsets = bn.getSynsets("bn:03083790n")
print(synsets[0].senses)

#getSenses is able to use after only getSynsets
senses = bn.getSenses("BabelNet", "EN")
print(senses[0])

#getSynsetIdsFromResourceID is able to use after only getSynsets
synset_ids = \
    bn.getSynsetIdsFromResourceID("BabelNet", "EN", "NOUN", "WIKI")
print(synset_ids[0])