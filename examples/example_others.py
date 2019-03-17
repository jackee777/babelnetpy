from babelnetpy.babelnet import BabelNet

bn = BabelNet(open("key.txt", "r").read())

Ids = bn.getSynset_Ids("BabelNet", "en")
print(Ids[0])

edges = bn.getOutgoingEdges(Ids[0].id)
print(edges[0])
