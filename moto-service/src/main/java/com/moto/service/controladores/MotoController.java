package com.moto.service.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moto.service.entidades.Moto;
import com.moto.service.servicios.MotoService;


@RestController
@RequestMapping("/moto")
public class MotoController {
	@Autowired
	private MotoService motoService;

	@GetMapping
	public ResponseEntity<List<Moto>> listarUsuario() {
		List<Moto> carros = motoService.getAll();
		if (carros.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(carros);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Moto> obtenerCarro(@PathVariable("id") int id) {
		Moto usuairo = motoService.getMotoById(id);
		if (usuairo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuairo);

	}

	@PostMapping
	public ResponseEntity<Moto> guardarMoto(@RequestBody Moto moto) {
		Moto nvCarro = motoService.save(moto);

		return ResponseEntity.ok(nvCarro);
	}

	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotosPorUsuarioId(@PathVariable("usuarioId") int id) {

		List<Moto> listMotos = motoService.byUsuarioId(id);

		if (listMotos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(listMotos);

	}
}
