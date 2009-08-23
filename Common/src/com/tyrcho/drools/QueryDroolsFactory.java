package com.tyrcho.drools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;

import com.tyrcho.drools.configuration.DroolsConfiguration;
import com.tyrcho.drools.configuration.DroolsFileConfiguration;
import com.tyrcho.drools.configuration.DroolsResourceType;

/**
 * Fabrique les interrogateurs de Drools.
 * 
 * @author daviotm
 */
public class QueryDroolsFactory {
	private static Logger logger = Logger.getLogger(QueryDroolsFactory.class);

	private static void addRuleFromStream(KnowledgeBuilder builder,
			InputStream stream, DroolsResourceType resourceType) {
		builder.add(ResourceFactory.newInputStreamResource(stream),
				ResourceType.getResourceType(resourceType.toString()));
	}

	public static IQueryDrools buildQueryDrools(Class<?> classWithResources,
			String... ruleFiles) {
		return new QueryDrools(initKnowledgeBuilder(ruleFiles,
				classWithResources));
	}

	public static IQueryDrools buildQueryDrools(String url,
			DroolsResourceType type) {
		return new QueryDrools(initKnowledgeBuilder(url, type));
	}

	public static IQueryDrools buildQueryDrools(
			DroolsConfiguration configuration) {
		QueryDrools queryDrools = new QueryDrools(
				initKnowledgeBuilder(configuration.getDroolFiles()));
		for (String errorHandlerClass : configuration.getErrorHandlerClasses()) {
			try {
				queryDrools.addErrorHandler((IDroolsErrorHandler) Class
						.forName(errorHandlerClass).newInstance());
				logger.info("ajout du ErrorHandler " + errorHandlerClass);
			} catch (InstantiationException e) {
				throw new IllegalArgumentException("Impossible d'instancier "
						+ errorHandlerClass, e);
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException("Impossible d'instancier "
						+ errorHandlerClass, e);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException("Classe non trouvée "
						+ errorHandlerClass, e);
			}
		}
		logger.debug("QueryDrools initialisé");
		return queryDrools;
	}

	private static KnowledgeBuilder initKnowledgeBuilder(
			DroolsFileConfiguration[] droolsFileConfigurations) {
		KnowledgeBuilder builder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		logger.debug("KnowledgeBuilder construit");
		for (DroolsFileConfiguration ruleFile : droolsFileConfigurations) {
			addRuleFile(builder, ruleFile);
		}
		logger.debug("KnowledgeBuilder initialisé");
		return builder;

	}

	private static KnowledgeBuilder initKnowledgeBuilder(String url,
			DroolsResourceType type) {
		KnowledgeBuilder builder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		logger.debug("KnowledgeBuilder construit");
		addRuleFile(builder, url, type);
		logger.debug("KnowledgeBuilder initialisé");
		return builder;
	}

	private static void addRuleFile(KnowledgeBuilder builder, String url,
			DroolsResourceType type) {
		try {
			addRuleFromStream(builder, new URL(url).openStream(), type);
			if (!builder.hasErrors()) {
				logger.info("URL " + url + " ajoutée");
			} else {
				StringBuilder stringBuilder = new StringBuilder();
				for (KnowledgeBuilderError error : builder.getErrors()) {
					stringBuilder.append(error);
				}
				throw new IllegalStateException(String
						.format("Erreur dans l'URL %s :%n%s%n", url,
								stringBuilder));
			}
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(
					"Impossible de charger les règles depuis : " + url, e);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Impossible de charger les règles depuis : " + url, e);
		}
	}

	private static void addRuleFile(KnowledgeBuilder builder,
			DroolsFileConfiguration ruleFile) {
		File file = new File(ruleFile.getFileName());
		try {
			InputStream stream = new FileInputStream(file);
			addRuleFromStream(builder, stream, ruleFile.getResourceType());
			if (!builder.hasErrors()) {
				logger.info("Fichier de règles " + file.getAbsolutePath()
						+ " ajouté");

			} else {
				StringBuilder stringBuilder = new StringBuilder();
				for (KnowledgeBuilderError error : builder.getErrors()) {
					stringBuilder.append(error);
				}
				throw new IllegalStateException(String.format(
						"Erreur dans le fichier %s :%n%s%n", file
								.getAbsoluteFile(), stringBuilder));
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Fichier de règle non trouvé : "
					+ file.getAbsolutePath());

		}
	}

	private static KnowledgeBuilder initKnowledgeBuilder(String[] ruleFiles,
			Class<?> classWithResources) {
		KnowledgeBuilder builder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		for (String ruleFile : ruleFiles) {
			InputStream stream = classWithResources
					.getResourceAsStream(ruleFile);
			if (stream != null) {
				addRuleFromStream(builder, stream, DroolsResourceType.DRL);
			} else {
				throw new IllegalArgumentException(
						"Fichier de règle non trouvé : " + ruleFile);
			}
		}
		return builder;

	}
}
