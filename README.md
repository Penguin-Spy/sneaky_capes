# Sneaky Capes
Renders custom cape textures hidden in the unused portions of the player's skin texture, avoiding the need (and dependency) of a 3rd party service to host the images.

This mod doesn't provide any cape textures itself, it only loads them from your skin texture! For a collection of official and unofficial cape textures (including the one in the mod icon), see https://penguinspy.dev/projects/sneaky_capes/

To insert a cape texture into your skin, i recommend using [Loom](https://penguinspy.dev/projects/loom/) (a skin editor i made):
- load your player's skin first using the "Load skin" dialog
- shift+click the "Load skin" button to show the hidden "Insert cape from file: " button
- insert the 64x32 pixel cape texture of your choosing; [the Minecraft Wiki's page on capes](https://minecraft.wiki/w/Cape) has all of the official cape textures,
    but you can use any cape/elytra texture you want.
- after the cape has been inserted, press the "Save image" button, save the file and then upload it as your skin at [minecraft.net](https://www.minecraft.net/en-us/msaprofile/mygames/editskin)
- any player using this mod will be able to see your custom cape. players not using this mod will see your account's actual cape (if any).

The usage of unused areas of the skin texture mostly doesn't conflict with how [Entity Texture Features](https://github.com/Traben-0/Entity_Texture_Features/blob/ETF-Main/.github/README-assets/SKIN_GUIDE.md) uses the unused areas; the markers are in different locations and the blinking textures don't overlap with the cape textures.

# License
Copyright © Penguin_Spy 2024

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
This Source Code Form is "Incompatible With Secondary Licenses", as
defined by the Mozilla Public License, v. 2.0.

The Covered Software may not be used as training or other input data
for LLMs, generative AI, or other forms of machine learning or neural
networks.
