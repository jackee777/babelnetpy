import json
import urllib.request
from pybabelnet.utils import dict2obj

class BabelNet(object):
    def __init__(self, key_path):
        self.API_PATH = "https://babelnet.io/v5/"
        self.synset_ids = []
        self.synsets = []
        self.senses = []
        #self.lemma = ""
        #self.lang = ""
        self.key = key_path
    
    def getSynset_Ids(self, lemma, lang):
        synset_url = self.API_PATH + \
            "getSynsetIds?lemma={0}&searchLang={1}&key={2}"
        synset_url = synset_url.format(lemma, lang, self.key)
        synset_json = urllib.request.urlopen(synset_url).read()
        synset_json = synset_json.decode("utf8")
        synset_ids = [dict2obj(js) for js in json.loads(synset_json)]
        self.synset_ids = synset_ids
        return synset_ids
    
    def getSynsets(self, synset_id):
        synset_url = self.API_PATH + \
            "getSynset?id={0}&key={1}"
        synset_url = synset_url.format(synset_id, self.key)
        synset_json = urllib.request.urlopen(synset_url).read()
        synset_json = synset_json.decode("utf8")
        synsets = [dict2obj(json.loads(synset_json))]
        self.synsets = synsets
        return synsets
    
    def getOutgoingEdges(self, synset_id):
        synset_url = self.API_PATH + \
            "getOutgoingEdges?id={0}&key={1}"
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