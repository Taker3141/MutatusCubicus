#version 400 core

in vec3 position;
in vec2 textureCoord;
in vec3 normal;

out vec2 passTextureCoord;
out vec3 surfaceNormal;
out vec3 toLightVector[4];
out vec3 toCameraVector;
out float visibility;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[4];
uniform float useFakeLightning;

const float density = 0.004;
const float gradient = 1.5;

void main(void)
{
	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	vec4 positionRelativeToCam = viewMatrix * worldPosition;
	gl_Position = projectionMatrix * positionRelativeToCam;
	passTextureCoord = textureCoord;
	
	vec3 actualNormal = normal;
	if(useFakeLightning > 0.5)
	{
		actualNormal = vec3(0.0, 0.1, 0.0);
	}
	
	surfaceNormal = (transformationMatrix * vec4(actualNormal, 0.0)).xyz;
	for(int i = 0; i < 4; i++)
	{	
		toLightVector[i] = lightPosition[i] - worldPosition.xyz;
	}
	toCameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;
	
	float distance = length(positionRelativeToCam.xyz);
	visibility = exp(-pow((distance * density), gradient));
	visibility = clamp(visibility, 0.0, 1.0);
}