package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

/**
 * Teste automatizado para pesquisar "Hello World" no Bing.
 */
public class BingSearchTest {

    private WebDriver driver;
    private WebDriverWait wait;

    /**
     * Configura o WebDriver antes de cada teste.
     * Inicializa o ChromeDriver e o WebDriverWait.
     */
    @BeforeEach
    public void setUp() {
        // Configura as opções do Chrome para rodar em modo headless (sem interface gráfica)
        // Isso é útil para ambientes de CI/CD ou execução em servidores sem display
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Executa em modo headless
        options.addArguments("--disable-gpu"); // Necessário para alguns sistemas operacionais
        options.addArguments("--window-size=1920,1080"); // Define o tamanho da janela
        options.addArguments("--no-sandbox"); // Necessário para rodar como root/em containers
        options.addArguments("--disable-dev-shm-usage"); // Supera limitações de recursos

        // Inicializa o WebDriver do Chrome com as opções configuradas
        driver = new ChromeDriver(options);
        
        // Configura um tempo de espera explícito (máximo 20 segundos)
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    /**
     * Teste principal: abre o Bing, pesquisa "Hello World", espera e fecha.
     */
    @Test
    public void testBingSearchHelloWorld() {
        try {
            // 10. Abrir a página inicial do Bing
            driver.get("https://www.bing.com/?cc=br");

            // Espera até que o campo de busca esteja visível e interagível
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sb_form_q")));

            // 11. Pesquisar na busca do Bing as palavras: Hello World
            searchBox.sendKeys("Hello World");
            searchBox.sendKeys(Keys.ENTER);

            // Espera até que os resultados da busca sejam carregados (verifica a presença do título da página de resultados)
            wait.until(ExpectedConditions.titleContains("Hello World"));

            // 12. Após pesquisar e retornar a busca, aguarde 10 segundos
            System.out.println("Pesquisa realizada. Aguardando 10 segundos...");
            Thread.sleep(10000); // Espera 10 segundos

        } catch (InterruptedException e) {
            // Trata a exceção caso a thread seja interrompida durante o sleep
            Thread.currentThread().interrupt();
            System.err.println("A espera foi interrompida: " + e.getMessage());
        } catch (Exception e) {
            // Captura outras exceções do Selenium ou gerais
            System.err.println("Ocorreu um erro durante o teste: " + e.getMessage());
            // Opcional: Falhar o teste em caso de erro
            // org.junit.jupiter.api.Assertions.fail("Erro durante o teste: " + e.getMessage());
        }
    }

    /**
     * Fecha o navegador após cada teste.
     */
    @AfterEach
    public void tearDown() {
        // 12. ... e feche o navegador.
        if (driver != null) {
            driver.quit();
            System.out.println("Navegador fechado.");
        }
    }
}
