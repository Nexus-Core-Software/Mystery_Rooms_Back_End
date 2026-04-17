package com.project.demo.logic.entity.reporte;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.project.demo.logic.entity.evento.EventoRepository;
import com.project.demo.logic.entity.evento.RecompensaEvento;
import com.project.demo.logic.entity.evento.RecompensaEventoRepository;
import com.project.demo.logic.entity.game.Game;
import com.project.demo.logic.entity.game.GameRepository;
import com.project.demo.logic.entity.game.GameStatus;
import com.project.demo.logic.entity.gift.GiftRepository;
import com.project.demo.logic.entity.giftList.GiftListRepository;
import com.project.demo.logic.entity.order.OrderRepository;
import com.project.demo.logic.entity.team.PlayerRepository;
import com.project.demo.logic.entity.team.TeamRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class ReporteService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final GameRepository gameRepository;
    private final OrderRepository orderRepository;
    private final GiftRepository giftRepository;
    private final GiftListRepository giftListRepository;
    private final EventoRepository eventoRepository;
    private final RecompensaEventoRepository recompensaEventoRepository;

    public ReporteService(
            PlayerRepository playerRepository,
            TeamRepository teamRepository,
            GameRepository gameRepository,
            OrderRepository orderRepository,
            GiftRepository giftRepository,
            GiftListRepository giftListRepository,
            EventoRepository eventoRepository,
            RecompensaEventoRepository recompensaEventoRepository
    ) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.gameRepository = gameRepository;
        this.orderRepository = orderRepository;
        this.giftRepository = giftRepository;
        this.giftListRepository = giftListRepository;
        this.eventoRepository = eventoRepository;
        this.recompensaEventoRepository = recompensaEventoRepository;
    }

    public ReporteEstadisticas collectStats() {
        ReporteEstadisticas stats = new ReporteEstadisticas();
        stats.setGeneratedAt(LocalDateTime.now());
        stats.setTotalPlayers(playerRepository.count());
        stats.setTotalTeams(teamRepository.count());
        stats.setTotalGames(gameRepository.count());
        stats.setTotalOrders(orderRepository.count());
        stats.setTotalGifts(giftRepository.count());
        stats.setTotalGiftLists(giftListRepository.count());
        stats.setTotalEventos(eventoRepository.count());
        stats.setTotalRecompensas(recompensaEventoRepository.count());
        stats.setTotalRewardPoints(calculateRewardPoints());
        stats.setActiveEventos(hasActiveEvent() ? 1L : 0L);
        stats.setGamesByStatus(countGamesByStatus());

        return stats;
    }

    public byte[] exportCsv() {
        ReporteEstadisticas stats = collectStats();
        String csvContent = buildCsv(stats);
        return csvContent.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] exportPdf() {
        ReporteEstadisticas stats = collectStats();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        document.add(new Paragraph("Reporte estadisticas del sistema"));
        document.add(new Paragraph("Generado: " + stats.getGeneratedAt()));
        document.add(new Paragraph("Total jugadores: " + stats.getTotalPlayers()));
        document.add(new Paragraph("Total equipos: " + stats.getTotalTeams()));
        document.add(new Paragraph("Total partidas: " + stats.getTotalGames()));
        document.add(new Paragraph("Total ordenes: " + stats.getTotalOrders()));
        document.add(new Paragraph("Total regalos: " + stats.getTotalGifts()));
        document.add(new Paragraph("Total listas de regalos: " + stats.getTotalGiftLists()));
        document.add(new Paragraph("Total eventos: " + stats.getTotalEventos()));
        document.add(new Paragraph("Eventos activos: " + stats.getActiveEventos()));
        document.add(new Paragraph("Total recompensas: " + stats.getTotalRecompensas()));
        document.add(new Paragraph("Total puntos recompensas: " + stats.getTotalRewardPoints()));

        Map<GameStatus, Long> statusCounts = stats.getGamesByStatus();
        if (statusCounts != null) {
            for (GameStatus status : GameStatus.values()) {
                Long count = statusCounts.getOrDefault(status, 0L);
                document.add(new Paragraph("Partidas " + status.name().toLowerCase(Locale.ROOT) + ": " + count));
            }
        }

        document.close();
        return outputStream.toByteArray();
    }

    private boolean hasActiveEvent() {
        LocalDateTime now = LocalDateTime.now();
        return eventoRepository
                .findFirstByActivoTrueAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(now, now)
                .isPresent();
    }

    private long calculateRewardPoints() {
        List<RecompensaEvento> recompensas = recompensaEventoRepository.findAll();
        long totalPoints = 0L;
        for (RecompensaEvento recompensa : recompensas) {
            Integer points = recompensa.getPuntos();
            if (points != null) {
                totalPoints += points;
            }
        }
        return totalPoints;
    }

    private Map<GameStatus, Long> countGamesByStatus() {
        EnumMap<GameStatus, Long> counts = new EnumMap<>(GameStatus.class);
        for (GameStatus status : GameStatus.values()) {
            counts.put(status, 0L);
        }

        List<Game> games = gameRepository.findAll();
        for (Game game : games) {
            GameStatus status = game.getStatus();
            if (status != null) {
                counts.put(status, counts.getOrDefault(status, 0L) + 1L);
            }
        }

        return counts;
    }

    private String buildCsv(ReporteEstadisticas stats) {
        StringBuilder builder = new StringBuilder();
        builder.append("metric,value\n");
        appendCsvLine(builder, "generated_at", String.valueOf(stats.getGeneratedAt()));
        appendCsvLine(builder, "total_players", String.valueOf(stats.getTotalPlayers()));
        appendCsvLine(builder, "total_teams", String.valueOf(stats.getTotalTeams()));
        appendCsvLine(builder, "total_games", String.valueOf(stats.getTotalGames()));
        appendCsvLine(builder, "total_orders", String.valueOf(stats.getTotalOrders()));
        appendCsvLine(builder, "total_gifts", String.valueOf(stats.getTotalGifts()));
        appendCsvLine(builder, "total_gift_lists", String.valueOf(stats.getTotalGiftLists()));
        appendCsvLine(builder, "total_eventos", String.valueOf(stats.getTotalEventos()));
        appendCsvLine(builder, "active_eventos", String.valueOf(stats.getActiveEventos()));
        appendCsvLine(builder, "total_recompensas", String.valueOf(stats.getTotalRecompensas()));
        appendCsvLine(builder, "total_reward_points", String.valueOf(stats.getTotalRewardPoints()));

        Map<GameStatus, Long> statusCounts = stats.getGamesByStatus();
        if (statusCounts != null) {
            for (GameStatus status : GameStatus.values()) {
                Long count = statusCounts.getOrDefault(status, 0L);
                appendCsvLine(builder, "games_status_" + status.name().toLowerCase(Locale.ROOT), String.valueOf(count));
            }
        }

        return builder.toString();
    }

    private void appendCsvLine(StringBuilder builder, String metric, String value) {
        builder.append(escapeCsv(metric))
                .append(',')
                .append(escapeCsv(value))
                .append("\n");
    }

    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }

        boolean needsQuotes = value.contains(",") || value.contains("\n") || value.contains("\r") || value.contains("\"");
        if (!needsQuotes) {
            return value;
        }

        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
