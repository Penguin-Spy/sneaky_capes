/* PlayerSkinProviderMixin.java © Penguin_Spy 2024
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

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.minecraft.MinecraftProfileTextures;
import net.minecraft.client.texture.PlayerSkin;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.util.Identifier;

@Mixin(PlayerSkinProvider.class)
public abstract class PlayerSkinProviderMixin {

	// registerTextures parses the texture data from the profile and returns a CompletableFuture that resolves to a PlayerSkin once all textures are loaded
	@Inject(method = "registerTextures(Ljava/util/UUID;Lcom/mojang/authlib/minecraft/MinecraftProfileTextures;)Ljava/util/concurrent/CompletableFuture;", at = @At("RETURN"), cancellable = true)
	public void registerTextures(UUID uuid, MinecraftProfileTextures textures, CallbackInfoReturnable<CompletableFuture<PlayerSkin>> cir) {
		// replace the CompletableFuture with one that returns the modified PlayerSkin
		cir.setReturnValue(cir.getReturnValue().thenApply(skin -> {
			// if the skin texture has a hidden cape, replace the cape texture identifier
			if(SneakyCapes.skinTexturesWithCapes.contains(skin.textureUrl())) {
				Identifier sneaky_cape = Identifier.of("sneaky_capes", skin.textureUrl().substring(skin.textureUrl().lastIndexOf("/")+1));
				SneakyCapes.LOGGER.info("replacing {}'s {} with {}", uuid, skin.capeTexture(), sneaky_cape);
				return new PlayerSkin(skin.texture(), skin.textureUrl(), sneaky_cape, skin.elytraTexture(), skin.model(), skin.secure());
			}
			return skin;  // else, just return the unmodified skin;
		}));
	}
}
