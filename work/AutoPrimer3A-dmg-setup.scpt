tell application "Finder"
  tell disk "AutoPrimer3A"
    open
    set current view of container window to icon view
    set toolbar visible of container window to false
    set statusbar visible of container window to false

    -- Set the size of the window to match the background
    set the bounds of container window to {400, 100, 917, 370}

    set theViewOptions to the icon view options of container window
    set arrangement of theViewOptions to not arranged
    set icon size of theViewOptions to 128

    -- Set background picture
    try
      set background picture of theViewOptions to file ".background:background.png"
    on error
      display dialog "Background image not found. Please check that the .background folder and background.png exist." buttons {"OK"}
    end try

    -- Create alias for install location
    try
      make new alias file at container window to POSIX file "/Applications" with properties {name:"Applications"}
    on error
      display dialog "Failed to create Applications alias." buttons {"OK"}
    end try

    -- Adjust positions of items in the DMG window
    set allTheFiles to items of container window
    repeat with theFile in allTheFiles
      set theFileName to name of theFile as text
      if theFileName is "AutoPrimer3A.app" then
        -- Position application
        set position of theFile to {120, 135}
      else if theFileName is "Applications" then
        -- Position Applications alias
        set position of theFile to {390, 135}
      else
        -- Move other files out of view
        set position of theFile to {1000, 0}
      end if
    end repeat

    close
    open
    update without registering applications
    delay 5
  end tell
end tell
