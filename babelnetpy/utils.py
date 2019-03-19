class dict2obj(dict):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        for key, value in self.items():
            if isinstance(value, dict):
                setattr(self, key, dict2obj(value))
            elif isinstance(value, list) and \
                len(value) != 0 and isinstance(value[0], dict):
                setattr(self, key, 
                        [dict2obj(val) for val in value])
            else:
                setattr(self, key, value)
                
"""
#this class does not respond the list class but responds the dict class to the object class
class dict2obj(dict):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        for key, value in self.items():
            if type(value) is dict:
                setattr(self, key, dict2obj(value))
            else:
                setattr(self, key, value)
"""