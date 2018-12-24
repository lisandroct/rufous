package org.rufousengine.graphics


class Model(meshes: Array<Mesh> = arrayOf(), materials: Array<Material> = arrayOf()) {
    private val _meshes = meshes.toMutableList()
    val meshes: List<Mesh> = _meshes
    private val _materials = materials.toMutableList()
    val materials: List<Material> = _materials

    private val meshesSet = meshes.toHashSet()
    private val materialsSet = materials.toHashSet()

    private val materialByMesh = hashMapOf<Mesh, Material>()

    fun addMesh(mesh: Mesh) {
        if(mesh in meshesSet) {
            throw IllegalArgumentException("mesh is already part of this model.")
        }

        _meshes.add(mesh)
        meshesSet.add(mesh)
    }

    fun addMaterial(material: Material) {
        if(material in materialsSet) {
            throw IllegalArgumentException("material is already part of this model.")
        }

        _materials.add(material)
        materialsSet.add(material)
    }

    fun removeMesh(mesh: Mesh) {
        if(mesh !in meshesSet) {
            throw IllegalArgumentException("mesh is not part of this model.")
        }

        _meshes.remove(mesh)
        meshesSet.remove(mesh)
        materialByMesh.remove(mesh)
    }

    fun removeMesh(index: Int) {
        if(index !in meshes.indices) {
            throw IndexOutOfBoundsException("index is out of bounds.")
        }

        val mesh = meshes[index]

        _meshes.removeAt(index)
        meshesSet.remove(mesh)
        materialByMesh.remove(mesh)
    }

    fun removeMaterial(material: Material) {
        if(material !in materialsSet) {
            throw IllegalArgumentException("material is not part of this model.")
        }

        _materials.remove(material)
        materialsSet.remove(material)
        for(mesh in meshes) {
            if(materialByMesh[mesh] == material) {
                materialByMesh.remove(mesh)
            }
        }
    }

    fun removeMaterial(index: Int) {
        if(index !in _materials.indices) {
            throw IndexOutOfBoundsException("index is out of bounds.")
        }

        val material = materials[index]

        _materials.removeAt(index)
        materialsSet.remove(material)
        for(mesh in meshes) {
            if(materialByMesh[mesh] == material) {
                materialByMesh.remove(mesh)
            }
        }
    }

    fun setMaterial(mesh: Mesh, material: Material) {
        if(mesh !in meshesSet) {
            throw IllegalArgumentException("mesh is not part of this model.")
        }
        if(material !in materialsSet) {
            throw IllegalArgumentException("material is not part of this model.")
        }

        materialByMesh[mesh] = material
    }

    fun setMaterial(mesh: Int, material: Int) {
        if(mesh !in meshes.indices) {
            throw IndexOutOfBoundsException("mesh is out of bounds.")
        }
        if(material !in _materials.indices) {
            throw IndexOutOfBoundsException("material is out of bounds.")
        }

        materialByMesh[_meshes[mesh]] = _materials[material]
    }

    fun getMaterial(mesh: Mesh): Material? {
        if(mesh !in meshesSet) {
            throw IllegalArgumentException("mesh is not part of this model.")
        }

        return materialByMesh[mesh]
    }

    fun getMaterial(mesh: Int): Material? {
        if(mesh !in meshes.indices) {
            throw IndexOutOfBoundsException("mesh is out of bounds.")
        }

        return materialByMesh[meshes[mesh]]
    }

    fun getMaterialIndex(mesh: Mesh): Int {
        if(mesh !in meshesSet) {
            throw IllegalArgumentException("mesh is not part of this model.")
        }

        return materials.indexOf(materialByMesh[mesh])
    }

    fun getMaterialIndex(mesh: Int): Int {
        if(mesh !in _meshes.indices) {
            throw IndexOutOfBoundsException("mesh is out of bounds.")
        }

        return materials.indexOf(materialByMesh[meshes[mesh]])
    }
}