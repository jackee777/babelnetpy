import json
import requests
from babelnetpy.utils import dict2obj

class BabelNet(object):
    def __init__(self, key_path):
        """
        API_PATH: base url
        synset_ids: babelnet synsetids
        synsets: babelnet synsets
        senses: babelnet word senses
        lang: language
        key: api key
        """        
        self.API_PATH = "https://babelnet.io/v5/"
        self.synset_ids = []
        self.synsets = []
        self.senses = []
        #self.lemma = ""
        self.lang = None
        self.key = key_path

    def make_url(self, **params):
        """
        this makes the target url that corresponds to the function
        
        params: lemma, id, lang, targetLang, pos, source
        lemma; word
        id: babelnet synsetids
        lang: language
        targetLang: target language that is often the same as lang; howerver rarely is not same.
        pos: part of speech
        source: wikipedia and so on
        lemma_type: full, simple
        force_compare: A == a. a big character is the same as a small character.
        
        return: target url
        """        
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
        """
        get synsetids to request target url
        
        lemma; word
        lang: language
        targetLang: target language that is often the same as lang; howerver rarely is not same.
        pos: part of speech
        source: wikipedia and so on
        
        return synsetsids
        """
        self.lang = lang
        synset_url = self.make_url(function="getSynsetIds?",
            lemma=lemma, lang=self.lang, pos=pos, source=source)
        synset_url = synset_url.format(lemma, self.lang, self.key)
        synset_json = requests.get(synset_url.encode('utf8', "backslashreplace").decode('utf8', "backslashreplace")).text
        synset_ids = [dict2obj(js) for js in json.loads(synset_json)]
        self.synset_ids = synset_ids
        return synset_ids

    def getSynsets(self, synset_id, targetLang=None, change_lang=False):
        """
        get synsets to request target url
        
        synset_id: a babelnet synsetid
        targetLang: target language that is often the same as lang; howerver rarely is not same.
        change_lang: for translation or sub language
        
        return synsets
        """        
        if change_lang and targetLang != None:
            if type(targetLang) == list:
                self.lang = targetLang[0]
            else:
                self.lang = targetLang
        targetLang = targetLang or self.lang
        synset_url = self.make_url(function="getSynset?",
            id=synset_id, targetLang=targetLang)
        synset_json = requests.get(synset_url.encode('utf8', "backslashreplace").decode('utf8', "backslashreplace")).text
        synsets = [dict2obj(json.loads(synset_json))]
        self.synsets = synsets
        return synsets

    def getOutgoingEdges(self, synset_id):
        """
        get edges that has relationships against other synsets to request target url
        
        synset_id: a babelnet synsetid
        
        return edges
        """     
        synset_url = self.make_url(function="getOutgoingEdges?",
            id=synset_id)
        synset_url = synset_url.format(synset_id, self.key)
        synset_json = requests.get(synset_url.encode('utf8', "backslashreplace").decode('utf8', "backslashreplace")).text
        edges = [dict2obj(js) for js in json.loads(synset_json)]
        self.edges = edges
        return edges
    
    def getWordNetId(self, synset_id):
        """
        For your internal server only,
        get WordNet id which is equal to BabelNet id
        
        synset_id: a babelnet synsetid
        
        return wordnet synsetid
        """
        assert("internal server only", self.key is None)
        synset_url = self.make_url(function="getWordnetId?",
            id=synset_id)
        synset_url = synset_url.format(synset_id, self.key)
        synset_json = requests.get(synset_url.encode('utf8', "backslashreplace").decode('utf8', "backslashreplace")).text
        wordnet_ids = [dict2obj(js) for js in json.loads(synset_json)]
        return wordnet_ids

    def getSenses(self, lemma, lang, lemma_type="full", force_compare=True, useURL=False):
        """
        get senses from synsets     
        
        lang: language
        pos: part of speech
        lemma_type: full, simple
        force_compare: A == a. a big character is the same as a small character.
        useURL: if it is True, it search from url. Unless, search from data has been already got  
        
        return: Senses
        """
        if useURL:
            lang = lang or self.lang
            synset_url = self.make_url(function="getSenses?",
                lemma=lemma, lang=lang)
            synset_json = requests.get(synset_url.encode('utf8', "backslashreplace").decode('utf8', "backslashreplace")).text
            senses = [dict2obj(js) for js in json.loads(synset_json)]
        else:
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
        """
        get synsetids from synsets and senses that has been already got 
        
        lemma; word
        lang: language
        pos: part of speech
        source: wikipedia and so on
        lemma_type: full, simple
        force_compare: A == a. a big character is the same as a small character.
        
        return: SynsetIds
        """
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
