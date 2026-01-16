package com.pagina.pagina.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.pagina.pagina.Modelos.RedesSociales;
import com.pagina.pagina.Repositorios.RedesSocialesRepositorio;

//Clase que inicializa datos por defecto en la base de datos
//En este caso se agregan las redes sociales por defecto para que el usuario pueda agregarlas

@Component
public class DataInitializer implements CommandLineRunner {

    private final RedesSocialesRepositorio redesSocialesRepositorio;

    public DataInitializer(RedesSocialesRepositorio redesSocialesRepositorio) {
        this.redesSocialesRepositorio = redesSocialesRepositorio;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen redes sociales en la base de datos por las dudas
        // claro esta
        if (redesSocialesRepositorio.count() == 0) {
            // Crear Instagram
            RedesSociales instagram = new RedesSociales();
            instagram.setNombre("Instagram");
            instagram.setIconoUrl("https://cdn.simpleicons.org/instagram");
            instagram.setUrl("https://instagram.com/tu_pagina");

            // Crear Facebook
            RedesSociales facebook = new RedesSociales();
            facebook.setNombre("Facebook");
            facebook.setIconoUrl("https://cdn.simpleicons.org/facebook");
            facebook.setUrl("https://facebook.com/tu_pagina");

            // Crear TikTok
            RedesSociales tiktok = new RedesSociales();
            tiktok.setNombre("TikTok");
            tiktok.setIconoUrl("https://cdn.simpleicons.org/tiktok");
            tiktok.setUrl("https://tiktok.com/@tu_pagina");

            // Guardar todas las redes sociales
            redesSocialesRepositorio.save(instagram);
            redesSocialesRepositorio.save(facebook);
            redesSocialesRepositorio.save(tiktok);

            System.out.println("✅ Redes sociales inicializadas correctamente: Instagram, Facebook, TikTok");
        } else {
            System.out.println("ℹ️ Las redes sociales ya están en la base de datos");
        }
    }
}
