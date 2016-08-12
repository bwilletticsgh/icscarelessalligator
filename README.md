# Kudos App

This project is generated with [yo angular generator](https://github.com/yeoman/generator-angular)
version 0.15.1.

# Setting up front end development environment:

 Download webstorm at: https://www.jetbrains.com/webstorm/download/download-thanks.html?platform=windows

### Open powershell and run the following. 
Note, you may need to open a new shell between commands

`Set-ExecutionPolicy RemoteSigned`
    
- install psget
`(new-object Net.WebClient).DownloadString("http://psget.net/GetPsGet.ps1") | iex`

- install chocolatey
`iwr https://chocolatey.org/install.ps1 -UseBasicParsing | iex`

- install node and npm
`cinst nodejs.install`

- install git
    `cinstall git`

- install posh-git
`Install-Module posh-git`

if posh-git doesn't seem to be working, just run this:
~~~
git clone https://github.com/dahlbyk/posh-git.git
cd posh-git
.\install.ps1
. $PROFILE
~~~

-install ruby
`cinst ruby`
`gem install compass`


-set up project
`cd \`
`mkdir development`
`cd development`

###Open webstorm
choose repository (for me: https://tclarkIR@icsdhsflash.git.cloudforge.com/dhsflash.git)
for parent dir pick C:\development
click clone

###back in powershell 
`cd \development\dhsflash\source\client` 
`npm install -g grunt grunt-cli bower karma`
`npm install`

## Build & development

Run `grunt` for building and `grunt serve` for preview.

## Testing

Running `grunt test` will run the unit tests with karma.
