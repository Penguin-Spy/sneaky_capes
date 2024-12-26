/* PlayerSkinManagerMixin.java © Penguin_Spy 2024
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 *
 * The Covered Software may not be used as training or other input data
 * for LLMs, generative AI, or other forms of machine learning or neural
 * networks.
 */
package dev.penguinspy.sneaky_capes.mixin;
import dev.penguinspy.sneaky_capes.SneakyCapes;

import java.util.concurrent.CompletableFuture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.blaze3d.texture.NativeImage;
import net.minecraft.client.texture.PlayerSkinManager;
import net.minecraft.util.Identifier;

@Mixin(PlayerSkinManager.class)
public class PlayerSkinManagerMixin {
	// putTexture (named by me) adds the image to the MinecraftClient's TextureManager
	@Shadow
	private static CompletableFuture<Identifier> putTexture(Identifier id, NativeImage image) { return null; }

	// formatSkinTexture (named by me) is called on all player textures that are skins,
	// and is normally responsible for removing transparency and converting legacy skin textures
	@Inject(method = "formatSkinTexture(Lcom/mojang/blaze3d/texture/NativeImage;Ljava/lang/String;)Lcom/mojang/blaze3d/texture/NativeImage;", at = @At("HEAD"))
	private static void formatSkinTexture(NativeImage skin, String url, CallbackInfoReturnable<NativeImage> ci) {
		if(skin.getWidth() == 64 && skin.getHeight() == 64) {
			// check for marker: #FFF42F, #FFFFFF, #9C59D1, #292929
			if(skin.getPixelABGR(60, 48) == 0xFFFFF42F
				&& skin.getPixelABGR(60, 49) == 0xFFFFFFFF
				&& skin.getPixelABGR(60, 50) == 0xFF9C59D1
				&& skin.getPixelABGR(60, 51) == 0xFF292929
			) {
				Identifier sneaky_cape = Identifier.of("sneaky_capes", url.substring(url.lastIndexOf("/")+1));
				SneakyCapes.LOGGER.info("marker was correct! creating texture {}", sneaky_cape);
				NativeImage cape = new NativeImage(64, 32, true);

          		// copy cape from skin
          		// front
				skin.copyRectangle(cape, 56,16,  1,1,  8,16, false, false);
				skin.copyRectangle(cape, 62, 0,  9,1,  2, 8, false, false);
				skin.copyRectangle(cape, 60, 0,  9,9,  2, 8, false, false);
				// back
				skin.copyRectangle(cape, 56,32, 12,1,  8,16, false, false);
				skin.copyRectangle(cape, 58, 0, 20,1,  2, 8, false, false);
				skin.copyRectangle(cape, 56, 0, 20,9,  2, 8, false, false);
				// left edge
				skin.copyRectangle(cape, 39, 0,  0,1,  1, 8, false, false);
				skin.copyRectangle(cape, 38, 0,  0,9,  1, 8, false, false);
				// right edge
				skin.copyRectangle(cape, 37, 0, 11,1,  1, 8, false, false);
				skin.copyRectangle(cape, 36, 0, 11,9,  1, 8, false, false);
				// top edge
				skin.copyRectangle(cape,  0,48,  1,0,  4, 1, false, false);
				skin.copyRectangle(cape, 12,48,  5,0,  6, 1, false, false);
				// bottom edge
				skin.copyRectangle(cape,  0,49, 11,0,  4, 1, false, false);
				skin.copyRectangle(cape, 12,49, 15,0,  6, 1, false, false);

				// copy elytra from skin
				// front  (10x20)
				skin.copyRectangle(cape,  0, 0, 36, 2,  8, 8, false, false);
				skin.copyRectangle(cape, 24, 0, 44, 2,  2, 8, false, false);
				skin.copyRectangle(cape, 26, 0, 36,10, 10, 8, false, false);
				skin.copyRectangle(cape, 44,48, 36,18,  8, 4, false, false);
				skin.copyRectangle(cape, 62,48, 44,18,  2, 4, false, false);
				// inside edge (2x12)
				skin.copyRectangle(cape, 18,48, 34, 2,  2, 4, false, false);
				skin.copyRectangle(cape, 28,48, 34, 6,  2, 4, false, false);
				skin.copyRectangle(cape, 30,48, 34,10,  2, 4, false, false);
				// outside edge (1x12)
				skin.copyRectangle(cape, 32,48, 22,10,  1, 4, false, false);
				skin.copyRectangle(cape, 33,48, 22,14,  1, 4, false, false);
				skin.copyRectangle(cape, 34,48, 22,18,  1, 4, false, false);
				// top edge (4x2)
				skin.copyRectangle(cape,  0,50, 30, 0,  4, 2, false, false);
				// bottom edge (6x2)
				skin.copyRectangle(cape, 12,50, 34, 0,  6, 2, false, false);

				putTexture(sneaky_cape, cape);
				SneakyCapes.skinTexturesWithCapes.add(url);
			}
		}
	}
}
