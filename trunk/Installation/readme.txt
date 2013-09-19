Installation [1.6.2] :

	I - Client :
	
		A - How to install Forge AND Optifine together :
		
			+- DropBox ----------------------------------------------------------------------------------------------------------------------------------------------------------------+
			| Step 1 : this files are available in "./DownloadedFiles/".                                                                                                               |
			| Just skip to step 2.                                                                                                                                                     |
			+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
			
			1. Download the following files :
				- Forge : "minecraftforge-universal-1.6.2-9.10.0.789.jar"
				- Optifine : "OptiFine_1.6.2_HD_U_B4.zip"
			
			2. Run "minecraftforge-universal-1.6.2-9.10.0.789.jar", check "Install client" and click Ok.
			
			3. Navigate to your ".minecraft" folder :
				- Windows : "%appdata%\.minecraft\"
				- Mac OS X : "~/Library/Application Support/minecraft/"
				- Linux : "~/.minecraft/"
			
			4. Navigate to the "versions" folder.
			
			+- DropBox ----------------------------------------------------------------------------------------------------------------------------------------------------------------+
			| Step 5 to 7 : the "1.6.2-Forge9.10.0.789-Optifine_HD_U_B4" folder is available in "./InstallationFiles/ForgeAndOptifine/".                                               |
			| Just copy-past it in the "versions" folder and skip to step 8.                                                                                                           |
			+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
			
			5. Make a copy of the "1.6.2-Forge9.10.0.789" folder and rename it to "1.6.2-Forge9.10.0.789-Optifine_HD_U_B4", then enter that folder and rename the files the same way :
				- "1.6.2-Forge9.10.0.789.jar" -> "1.6.2-Forge9.10.0.789-Optifine_HD_U_B4.jar"
				- "1.6.2-Forge9.10.0.789.json" -> "1.6.2-Forge9.10.0.789-Optifine_HD_U_B4.json"
			
			6. Open "1.6.2-Forge9.10.0.789-Optifine_HD_U_B4.json" with a text editor and replace the id "1.6.2-Forge9.10.0.789" by "1.6.2-Forge9.10.0.789-Optifine_HD_U_B4".
			
			7. Copy all the content of "OptiFine_1.6.2_HD_U_B4.zip" in "1.6.2-Forge9.10.0.789-Optifine_HD_U_B4.jar" and delete the META-INF folder.
			
			+- DropBox ----------------------------------------------------------------------------------------------------------------------------------------------------------------+
			| Step 8 : if mincraft fails to download a library, you can find it in "./InstallationFiles/libraries/".                                                                   |
			| You can just copy-past the whole "libraries" folder in your ".minecraft" folder. But don't skip the whole step!                                                          |
			+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
			
			8. Run the Minecraft Launcher and create a new profile, tell it to use the "release 1.6.2-Forge9.10.0.789-Optifine_HD_U_B4" version.
			Under Java settings, check JVM Arguments and add "-Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.ignorePatchDiscrepancies=true" after "-Xmx1G" or whatever you may have.
			(You can add some arguments to allocate more RAM to the client, for exemple : "-Xmx2G -Xms2G -Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.ignorePatchDiscrepancies=true" allocates 2G to the client.)
			Save Profile, select your new profile and Play the game!
			
			9. Forge and Optifine are now installed together on your client. You can now add mods in the "mods" folder.
		
		B - How to install StargateMod :
		
			1. Navigate to your ".minecraft" folder.
			
			+- DropBox ----------------------------------------------------------------------------------------------------------------------------------------------------------------+
			| Step 2 : the file "[1.6.2]SeigneurNecronStargateMod_v3.0.0.zip" is available in "./InstallationFiles/".                                                                  |
			+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
			
			2. Put the file "[1.6.2]SeigneurNecronStargateMod_v3.0.0.zip" in the "mods" folder.
			
			3. Start Minecraft with your Forge+Optifine profil. Enjoy !
		
		C - How to install the texture pack :
		
			1. Navigate to your ".minecraft" folder.
			
			+- DropBox ----------------------------------------------------------------------------------------------------------------------------------------------------------------+
			| Step 2 : the file "[1.6.2]SeigneurNecronStargateMod_v3.0.0_Textures.zip" is available in "./InstallationFiles/".                                                         |
			| TIP : the complete texture pack "NecronCraft 64.zip" is also available in "./InstallationFiles/".                                                                        |
			+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
			
			2. Put the file "[1.6.2]SeigneurNecronStargateMod_v3.0.0_Textures.zip" in the "resourcepacks" folder.
			
			3. Start Minecraft with your Forge+Optifine profil. Enjoy !
	
	II - Server :
	
		A - How to install Forge :
		
			+- DropBox ----------------------------------------------------------------------------------------------------------------------------------------------------------------+
			| Step 1 : this file is available in "./DownloadedFiles/".                                                                                                                 |
			| Just skip to step 2.                                                                                                                                                     |
			+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
			
			1. Download the following file : "minecraftforge-installer-1.6.2-9.10.0.789.jar".
			
			+- DropBox ----------------------------------------------------------------------------------------------------------------------------------------------------------------+
			| Step 2 : if mincraft forge fails to download a library, you can find it in "./InstallationFiles/libraries/".                                                             |
			| You can just copy-past the whole "libraries" folder in your server folder. But don't skip the whole step!                                                                |
			+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
			
			2. Run "minecraftforge-universal-1.6.2-9.10.0.789.jar", check "Install server", select the folder in which you want to install your server and click Ok.
			
			3. Forge is now installed on your server. You can now add mods in the "mods" folder.
		
		B - How to install StargateMod :
		
			1. Navigate to your server folder.
			
			+- DropBox ----------------------------------------------------------------------------------------------------------------------------------------------------------------+
			| Step 2 : the file "[1.6.2]SeigneurNecronStargateMod_v3.0.0.zip" is available in "./InstallationFiles/".                                                                  |
			+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
			
			2. Put the file "[1.6.2]SeigneurNecronStargateMod_v3.0.0.zip" in the "mods" folder.
			
			3. Start your server. Enjoy !
	
	III - Modding :
	
		A - How to install Forge :
		
			+- DropBox ----------------------------------------------------------------------------------------------------------------------------------------------------------------+
			| Step 1 : this file is available in "./DownloadedFiles/".                                                                                                                 |
			| Just skip to step 2.                                                                                                                                                     |
			+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
			
			1. Download the following file : "minecraftforge-src-1.6.2-9.10.0.789.zip".
			
			2. Extract the content of "minecraftforge-src-1.6.2-9.10.0.789.zip" (a "forge" folder) where you whant to place your workspace.
			
			3. Follow "README-MinecraftForge.txt" in that folder.
			(You just have to launch a install script which will do all the work for you, and create an eclipse workspace in "forge/mcp/eclipse/".)
			
			4. Now it is your time to work ! Open Eclipse and go make an awesome mod ! ^^

End of file.