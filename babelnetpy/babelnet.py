import json
import urllib.request
from babelnetpy.utils import dict2obj

class BabelNet(object):
    def __init__(self, key_path):
        self.API_PATH = "https://babelnet.io/v5/"
        self.synset_ids = []
        self.synsets = []
        self.senses = []
        #self.lemma = ""
        self.lang = None
        self.key = key_path

    def make_url(self, **params):
        synset_url = self.API_PATH
        synset_url += params["function"]
        if params.get("lemma"):
            synset_url += "lemma={0}".format(params["lemma"])
        if params.get("id"):
            synset_url += "id={0}".format(params["id"])
        if params.get("lang"):
            synset_url += "&searchLang={0}".format(params["lang"])
        if params.get("targetLang") and params["targetLang"]!=None:
            if type(params["targetLang"]) == list:
                assert len(params["targetLang"]) == 2, "targetLang is not more than 3"
                synset_url += "&targetLang={0}".format(params["targetLang"][0])
                synset_url += "&targetLang={0}".format(params["targetLang"][1])
            else:
                synset_url += "&targetLang={0}".format(params["targetLang"])
        if params.get("pos") and params["pos"]!=None:
            if type(params["pos"]) == list:
                for pos in params["pos"]:
                    synset_url += "&pos={0}".format(pos)
            else:
                synset_url += "&pos={0}".format(params["pos"])
        if params.get("source") and params["source"]!=None:
            if type(params["source"]) == list:
                for source in params["source"]:
                    synset_url += "&source={0}".format(source)
            else:
                synset_url += "&source={0}".format(params["source"])

        synset_url += "&key={0}".format(self.key)
        return synset_url

    def getSynset_Ids(self, lemma, lang, targetLang=None, pos=None, source=None):
        self.lang = lang
        synset_url = self.make_url(function="getSynsetIds?",
            lemma=lemma, lang=self.lang, pos=pos, source=source)
        synset_url = synset_url.format(lemma, self.lang, self.key)
        synset_json = urllib.request.urlopen(synset_url).read()
        synset_json = synset_json.decode("utf8")
        synset_ids = [dict2obj(js) for js in json.loads(synset_json)]
        self.synset_ids = synset_ids
        return synset_ids

    def getSynsets(self, synset_id, targetLang=None, change_lang=False):
        if change_lang and targetLang != None:
            if type(targetLang) == list:
                self.lang = targetLang[0]
            else:
                self.lang = targetLang
        targetLang = targetLang or self.lang
        synset_url = self.make_url(function="getSynset?",
            id=synset_id, targetLang=targetLang)
        synset_json = urllib.request.urlopen(synset_url).read()
        synset_json = synset_json.decode("utf8")
        synsets = [dict2obj(json.loads(synset_json))]
        self.synsets = synsets
        return synsets

    def getOutgoingEdges(self, synset_id):
        synset_url = self.make_url(function="getOutgoingEdges?",
            id=synset_id)
        synset_url = synset_url.format(synset_id, self.key)
        synset_json = urllib.request.urlopen(synset_url).read()
        synset_json = synset_json.decode("utf8")
        edges = [dict2obj(js) for js in json.loads(synset_json)]
        self.edges = edges
        return edges

    def getSenses(self, lemma, lang, lemma_type="full", force_compare=True):
        senses = []
        if "full" == lemma_type:
            for synset in self.synsets:
                for sense in synset.senses:
                    if force_compare and \
                        sense.properties.fullLemma.lower() == lemma.lower() and \
                        sense.properties.language.lower() == lang.lower():
                        senses.append(sense)
                    elif sense.properties.fullLemma == lemma and \
                        sense.properties.language == lang:
                        senses.append(sense)
        elif "simple" == lemma_type:
            for synset in self.synsets:
                for sense in synset.senses:
                    if force_compare and \
                        sense.properties.fullLemma.lower() == lemma.lower() and \
                        sense.properties.language.lower() == lang.lower():
                        senses.append(sense)
                    elif sense.properties.simpleLemma == lemma and \
                        sense.properties.language == lang:
                        senses.append(sense)
        self.senses = senses
        return senses

    def getSynsetIdsFromResourceID(self, lemma, lang, pos, source, \
        lemma_type="full", force_compare=True):

        synset_ids = []
        if "full" == lemma_type:
            for synset in self.synsets:
                for sense in synset.senses:
                    if force_compare and \
                        sense.properties.fullLemma.lower() == lemma.lower() and \
                        sense.properties.language == lang and \
                        sense.properties.pos == pos and \
                        sense.properties.source == source:
                        synset_ids.append(sense.properties.synsetID)
                    elif sense.properties.fullLemma == lemma and \
                        sense.properties.language == lang and \
                        sense.properties.pos == pos and \
                        sense.properties.source == source:
                        synset_ids.append(sense.properties.synsetID)
        elif "simple" == lemma_type:
            for synset in self.synsets:
                for sense in synset.senses:
                    if force_compare and \
                        sense.properties.fullLemma.lower() == lemma.lower() and \
                        sense.properties.language == lang and \
                        sense.properties.pos == pos and \
                        sense.properties.source == source:
                        synset_ids.append(sense.properties.synsetID)
                    elif sense.properties.fullLemma == lemma and \
                        sense.properties.language == lang and \
                        sense.properties.pos == pos and \
                        sense.properties.source == source:
                        synset_ids.append(sense.properties.synsetID)
        return synset_ids
