# Java version example

Unfortunately, python cannot get the information from BABELNET for free.
I put java offline version here.

# SOURCE DOWNLOAD
## WORDNET
download from https://wordnet.princeton.edu/download/current-version#nix
```
cd /opt
sudo tar -jxvf /your dir/WordNet-3.0.tar.bz2
```

## BABELNET
download from https://babelnet.org/home
```
cd /opt
sudo tar -jxvf /your dir/babelnet-4.0.1-index.tar.bz2
```


# INFORMATION
https://github.com/marcevrard/BabelNet-API
## ECLIPSE
1.1 Configuring BabelNet API within an Eclipse project

Create your Eclipse project (File -> New -> Java project, give the project a name and press Finish). This creates a new folder with the project name projectFolder/ under your Eclipse workspace folder.

Copy the config/ and resources/ folders from the BabelNet-API-4.0 folder into your workspace/projectFolder/

Now we need to include all the lib/*.jar files and the babelnet-api-4.0.jar file in the project build classpath:

Select the project from 'Package Explorer' tree view

From the top bar click on 'Project' and then 'Properties'

Once inside the 'Properties' section click on 'Java build path' and select the 'Libraries' tab

From the right menu click on the 'Add External JARs' button

Browse to the downloaded BabelNet-API-4.0 folder, and select all the lib/*.jar files along with the babelnet-api-4.0.jar file

Next we need to include the config/ folder in the project build classpath:

Select the project from 'Package Explorer' tree view

From the top bar click on 'File' and then 'Refresh'

From the 'Java build path' (see point 3 above) select the 'Source' tab

Once in the 'Source' tab, click on 'Add Folder' from the right sidebar and select the downloaded config/ folder

Happy coding!! ;-)

For more information consult the guide online, http://babelnet.org/guide


## BABELNET
In "config/babelnet.var.properties"
```
babelnet.dir=/opt/BabelNet-4.0.1

# comment out
#babelnet.restfulurl=http://babelnet.io/v5/service
```

## WORDNET
In "config/babelnet.var.properties"
```
jlt.wordnetPrefix=/opt/WordNet
```

You are now ready to use the API with local indices.
