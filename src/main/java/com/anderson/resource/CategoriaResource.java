package com.anderson.resource;


import com.anderson.model.Categoria;
import com.anderson.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;


/**
 * Created by Anderson on 29/05/2019.
 */

// - C

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> listar() {
        return categoriaRepository.findAll();



        /* EXEMPLOS DE TRATAMENTO PARA LISTA VAZIA

        @GetMapping
        public ReponseEntity<?> listar() {
        List<Categoria>categorias = CategoriaRepository.findAll();
        return !categorias.isEmpty() ? ResponseEntity.ok(categorias): ReponseEntity.notFound().build; // retorna 404



        @GetMapping
        public ReponseEntity<?> listar() {
        List<Categoria>categorias = CategoriaRepository.findAll();
        return !categorias.isEmpty() ? ResponseEntity.ok(categorias): ReponseEntity.notContent().build; // retorna 204

        */
    }

   /* POST VERSÃO 1

   @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retorna 201 created
    public void salvarCategoria(@RequestBody Categoria categoria){

        categoriaRepository.save(categoria);

        */

       //-------------------------------------------------------------------------


        /* RETORNA A CATEGORIA QUE FOI SALVA

       @PostMapping
        public void salvarCategoria(@RequestBody Categoria categoria){

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        */

    //------------------------------------------------------------------------------

    // POST VERSAO 2 COMLETA

    @PostMapping
    public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response) {
        Categoria categoriaSalva = categoriaRepository.save(categoria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(categoriaSalva.getCodigo()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(categoriaSalva);
    }

    @GetMapping("/{codigo}")
    public Categoria buscarPeloCodigo(@PathVariable Long codigo) {
        return this.categoriaRepository.findById(codigo).orElse(null);
    }

}