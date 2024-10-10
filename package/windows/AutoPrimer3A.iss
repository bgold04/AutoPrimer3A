;This file will be executed next to the application bundle image
;I.e. current directory will contain folder AutoPrimer3 with application files
[Setup]
AppId={{AutoPrimer3A}}
AppName=AutoPrimer3A
AppVersion=3A
AppVerName=AutoPrimer3A
AppPublisher=David A. Parry
AppComments=AutoPrimer3A
AppCopyright=Copyright (C) 2015
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName={localappdata}\AutoPrimer3A
DisableStartupPrompt=Yes
DisableDirPage=Yes
DisableProgramGroupPage=Yes
DisableReadyPage=Yes
DisableFinishedPage=Yes
DisableWelcomePage=Yes
DefaultGroupName=AutoPrimer3A
;Optional License
LicenseFile=
;WinXP or above
MinVersion=0,5.1 
OutputBaseFilename=AutoPrimer3A
Compression=lzma
SolidCompression=yes
PrivilegesRequired=lowest
SetupIconFile=AutoPrimer3\AutoPrimer3A.ico
UninstallDisplayIcon={app}\AutoPrimer3A.ico
UninstallDisplayName=AutoPrimer3A
WizardImageStretch=No
WizardSmallImageFile=AutoPrimer3A-setup-icon.bmp   
ArchitecturesInstallIn64BitMode=x64

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "AutoPrimer3A\AutoPrimer3A.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "AutoPrimer3A\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\AutoPrimer3A"; Filename: "{app}\AutoPrimer3A.exe"; IconFilename: "{app}\AutoPrimer3.ico"; Check: returnTrue()
Name: "{commondesktop}\AutoPrimer3A"; Filename: "{app}\AutoPrimer3A.exe";  IconFilename: "{app}\AutoPrimer3A.ico"; Check: returnFalse()

[Run]
Filename: "{app}\AutoPrimer3A.exe"; Description: "{cm:LaunchProgram,AutoPrimer3A}"; Flags: nowait postinstall skipifsilent; Check: returnTrue()
Filename: "{app}\AutoPrimer3A.exe"; Parameters: "-install -svcName ""AutoPrimer3A"" -svcDesc ""AutoPrimer3A"" -mainExe ""AutoPrimer3A.exe""  "; Check: returnFalse()

[UninstallRun]
Filename: "{app}\AutoPrimer3A.exe "; Parameters: "-uninstall -svcName AutoPrimer3A -stopOnUninstall"; Check: returnFalse()

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support? 
  Result := True;
end;  
