#version 400 core

in vec2 textureCoords;

out vec4 outColor;

uniform sampler2D guiTexture;
uniform float height;
uniform float width;
uniform float offsetX;
uniform float offsetY;

void main(void)
{
	vec4 textureColor = texture(guiTexture, vec2(textureCoords.x * width + offsetX, textureCoords.y * height + offsetY));
	outColor = textureColor;
	if(textureColor.a < 0.5)
	{
		//discard;
	}
}
