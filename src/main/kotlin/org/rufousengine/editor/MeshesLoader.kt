package org.rufousengine.editor

import org.lwjgl.assimp.AIMesh
import org.lwjgl.assimp.Assimp.*
import org.lwjgl.system.MemoryUtil
import org.rufousengine.files.File
import org.rufousengine.graphics.Mesh
import org.rufousengine.graphics.VertexAttribute

object MeshesLoader {
    fun load(file: File, mask: Int = VertexAttribute.mask(VertexAttribute.position, VertexAttribute.normal, VertexAttribute.uv)) : Array<Mesh> {
        if(!file.exists || !file.isFile) {
            throw Exception("Error loading function")
        }

        val fileBytes = file.readBytes()
        val buffer = MemoryUtil.memAlloc(fileBytes.size)
        buffer.put(fileBytes)
        buffer.flip()

        val aiScene = aiImportFileFromMemory(buffer, aiProcess_Triangulate or aiProcess_FlipUVs or aiProcess_JoinIdenticalVertices, file.extension) ?: throw Exception("Error loading function")

        MemoryUtil.memFree(buffer)

        if((aiScene.mFlags() and AI_SCENE_FLAGS_INCOMPLETE) != 0) {
            throw Exception("Error loading function")
        }

        val aiMeshes = aiScene.mMeshes() ?: return arrayOf()

        val meshes = mutableListOf<Mesh>()
        while(aiMeshes.remaining() > 0) {
            val aiMesh = AIMesh.create(aiMeshes.get())
            meshes.add(processMesh(aiMesh, mask))
        }

        aiFreeScene(aiScene)

        return meshes.toTypedArray()
    }

    private fun processMesh(aiMesh: AIMesh, mask: Int) : Mesh {
        val aiPositions = aiMesh.mVertices()
        val count = aiPositions.count()
        val positions = VertexAttribute.position.isIn(mask)

        val aiNormals = aiMesh.mNormals()
        val normals = VertexAttribute.normal.isIn(mask) && aiNormals != null
        val aiUVs = aiMesh.mTextureCoords(0)
        val uvs = VertexAttribute.uv.isIn(mask) && aiUVs != null

        val vertices = mutableListOf<Float>()

        for(i in 0 until count) {
            if(positions) {
                val aiPosition = aiPositions.get(i)
                vertices.add(aiPosition.x())
                vertices.add(aiPosition.y())
                vertices.add(aiPosition.z())
            }

            if(normals) {
                val aiNormal = aiNormals!!.get(i)
                vertices.add(aiNormal.x())
                vertices.add(aiNormal.y())
                vertices.add(aiNormal.z())
            }

            if(uvs) {
                val aiUV = aiUVs!!.get(i)
                vertices.add(aiUV.x())
                vertices.add(aiUV.y())
            }
        }

        val aiFaces = aiMesh.mFaces()

        val indices = mutableListOf<Int>()

        while(aiFaces.remaining() > 0) {
            val aiFace = aiFaces.get()
            val aiIndices = aiFace.mIndices()
            while(aiIndices.remaining() > 0) {
                indices.add(aiIndices.get())
            }
        }

        val attributes = mutableListOf<VertexAttribute>()
        if(positions) {
            attributes.add(VertexAttribute.position)
        }
        if(normals) {
            attributes.add(VertexAttribute.normal)
        }
        if(uvs) {
            attributes.add(VertexAttribute.uv)
        }

        return Mesh(vertices.toFloatArray(), indices.toIntArray(), VertexAttribute.mask(attributes))
    }
}