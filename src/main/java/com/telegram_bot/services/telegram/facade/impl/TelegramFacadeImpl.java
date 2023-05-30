package com.telegram_bot.services.telegram.facade.impl;

import com.telegram_bot.global.enums.menu.PartnersMenu;
import com.telegram_bot.global.interfaces.SendMessageCreator;
import com.telegram_bot.global.enums.datetime.DayOfWeekRus;
import com.telegram_bot.global.enums.menu.BookingsMenu;
import com.telegram_bot.global.enums.menu.SignUpMenu;
import com.telegram_bot.global.enums.menu.StartMenu;
import com.telegram_bot.global.enums.operation.Operation;
import com.telegram_bot.global.util.service.DateTimeUtil;
import com.telegram_bot.services.booking.model.entity.Booking;
import com.telegram_bot.services.booking.service.BookingService;
import com.telegram_bot.services.menu.service.main_menu.MenuCreatorService;
import com.telegram_bot.services.menu.service.booking.BookingMenuCreator;
import com.telegram_bot.services.menu.service.partner.MenuPartnerCreator;
import com.telegram_bot.services.menu.service.sign_up.SignUpMenuCreator;
import com.telegram_bot.services.partner.model.entity.PartnerSearchRequest;
import com.telegram_bot.services.partner.service.PartnerService;
import com.telegram_bot.services.telegram.facade.TelegramFacade;
import com.telegram_bot.services.handler.user.UserHandler;
import com.telegram_bot.services.user.model.entity.UserEntity;
import com.telegram_bot.services.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class TelegramFacadeImpl implements TelegramFacade {

    UserHandler userHandler;
    BookingService bookingService;
    UserService userService;
    MenuCreatorService menuCreatorService;
    BookingMenuCreator bookingMenuCreator;
    SignUpMenuCreator signUpMenuCreator;
    SendMessageCreator sendMessageCreator;
    DateTimeUtil dateTimeUtil;
    PartnerService partnerService;
    MenuPartnerCreator menuPartnerCreator;

    //добавить aop для userHandler
    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage sendMessage = null;
        if (update.hasMessage()){
            Message message = update.getMessage();
            User telegramUser = message.getFrom();
            userHandler.handleUserInfo(telegramUser);
            sendMessage = handleInputMessage(message);
        }else if (update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            User telegramUser = callbackQuery.getFrom();
            userHandler.handleUserInfo(telegramUser);
            UserEntity user = userService.getUserByTelegramId(telegramUser.getId()).orElseThrow();
            sendMessage = handleInputCallbackQuery(callbackQuery, user);
        }
        return sendMessage;
    }

    private SendMessage handleInputCallbackQuery(CallbackQuery callbackQuery, UserEntity user) {
        String command = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();
        log.info("Command: {}, chat id: {}", command, chatId);
        if (command.contains(StartMenu.MY_BOOKINGS.name())){
            LocalDate currentDate = LocalDate.now();
            LocalDate lastDayOfWeek = dateTimeUtil.getLastDayOfWeek();
            List<Booking> bookings = bookingService.getBookingsForUserByBetweenDates(currentDate, lastDayOfWeek, user);
            InlineKeyboardMarkup inlineKeyboardMarkup =
                    bookingMenuCreator.createInlineKeyboardMarkupList(bookings, user);
            String text = String.format(BookingsMenu.ALL_BOOKINGS.getText(), currentDate, lastDayOfWeek);
            return sendMessageCreator.createSendMessageBody(chatId, inlineKeyboardMarkup, text);
        }if (command.contains(StartMenu.SIGN_UP_FREE_SLOT.name())){
            List<DayOfWeekRus> dayOfWeekRusList = dateTimeUtil.generateListDayOfWeekAfterToday();
            InlineKeyboardMarkup inlineKeyboardMarkup =
                    signUpMenuCreator.createInlineKeyboardMarkupDaysOfWeek(dayOfWeekRusList);
            String text = "Выберите день недели";
            return sendMessageCreator.createSendMessageBody(chatId, inlineKeyboardMarkup, text);
        }if (command.contains(StartMenu.PARTNERS.name())){
            return SendMessage
                    .builder()
                    .chatId(chatId)
                    .replyMarkup(menuCreatorService.createInlineKeyboardMarkup(PartnersMenu.class))
                    .text("Выберите функцию")
                    .build();
        }if (command.contains(PartnersMenu.CREATE_PARTNER_SEARCH_REQUEST.name())){
            List<DayOfWeekRus> dayOfWeekRusList = dateTimeUtil.generateListDayOfWeekAfterToday();
            InlineKeyboardMarkup inlineKeyboardMarkup =
                    signUpMenuCreator.createInlineKeyboardMarkupDaysOfWeekForSearchPartner(dayOfWeekRusList);
            String text = "Выберите день недели";
            return sendMessageCreator.createSendMessageBody(chatId, inlineKeyboardMarkup, text);
        }if (command.contains(Operation.SELECT_DAY_OF_WEEK_PARTNER_SEARCH_REQUEST.name())){
            String[] paramsArray = command.split("&");
            final String dateStr = paramsArray[1];
            if (StringUtils.hasLength(dateStr)){
                LocalDate date = LocalDate.parse(dateStr);
                List<LocalTime> freeTimes = bookingService.getFreeSlotsByDate(date);
                if (!userService.bookingLimitNotExceeded(user, date)){
                    String text = SignUpMenu.BOOKING_LIMIT_EXCEEDED.getText();
                    return sendMessageCreator.createSendMessageBody(chatId, text);
                }
                if (freeTimes.size() == 0){
                    String text = String.format(SignUpMenu.NO_FREE_SLOTS.getText(), date.toString());
                    return sendMessageCreator.createSendMessageBody(chatId, text);
                }
                InlineKeyboardMarkup inlineKeyboardMarkup = signUpMenuCreator.createInlineKeyboardMarkupFreeSlotsForPartnerSearch(freeTimes, date);
                String text = "Свободные слоты на " + date;
                return sendMessageCreator.createSendMessageBody(chatId, inlineKeyboardMarkup, text);
            }
        }if (command.contains(Operation.SELECT_TIME_PARTNER_SEARCH_REQUEST.name())){
            String[] paramsArray = command.split("&");
            final String selectTimeStr = paramsArray[1];
            if (StringUtils.hasLength(selectTimeStr)){
                LocalDateTime dateTime = LocalDateTime.parse(selectTimeStr);
                try {
                    partnerService.createRequestForPartner(user, dateTime);
                    String text = "Вы успешно создали заявку на " + dateTime;
                    return sendMessageCreator.createSendMessageBody(chatId, text);
                }catch (Exception e){
                    String text = "Не удалось создать заявку!";
                    return sendMessageCreator.createSendMessageBody(chatId, text);
                }
            }
        }if (command.contains(PartnersMenu.MY_PARTNER_SEARCH_REQUESTS.name())){
            List<PartnerSearchRequest> applications = partnerService.getApplicationsUser(user);
            InlineKeyboardMarkup inlineKeyboardMarkup =
                    menuPartnerCreator.createInlineKeyboardMarkupUserApplications(applications);
            String text = "Мои заявки поиска напарников";
            return sendMessageCreator.createSendMessageBody(chatId, inlineKeyboardMarkup, text);
        }if (command.contains(Operation.CANCEL_PARTNER_SEARCH_REQUEST.name())){
            String[] paramsArray = command.split("&");
            final String partnerSearchRequestIdStr = paramsArray[1];
            if (StringUtils.hasLength(partnerSearchRequestIdStr)){
                long id = Long.parseLong(partnerSearchRequestIdStr);
                partnerService.deleteApplication(id);
                String text = "Заявка отменена!";
                return sendMessageCreator.createSendMessageBody(chatId, text);
            }
        }if (command.contains(PartnersMenu.SHOW_EXISTS_PARTNER_SEARCH_REQUESTS.name())){
            List<PartnerSearchRequest> applications = partnerService.getAllFreeApplicationsByDate(LocalDate.now());
            InlineKeyboardMarkup inlineKeyboardMarkup =
                    menuPartnerCreator.createInlineKeyboardMarkupAllApplications(applications);
            String text =String.format( "Заявки для поиска напарников на %s", LocalDate.now());
            return sendMessageCreator.createSendMessageBody(chatId, inlineKeyboardMarkup, text);
        }if(command.contains(Operation.SELECT_PARTNER_SEARCH_REQUEST.name())){
            String[] paramsArray = command.split("&");
            final String partnerSearchRequestIdStr = paramsArray[1];
            if (StringUtils.hasLength(partnerSearchRequestIdStr)){
                long id = Long.parseLong(partnerSearchRequestIdStr);
                partnerService.becomePartner(user, id);
                String text = "Вы стали напарником!";
                return sendMessageCreator.createSendMessageBody(chatId, text);
            }
        } if (command.contains(Operation.DAY_OF_WEEK_SELECTION.name())){
            String[] paramsArray = command.split("&");
            final String dateStr = paramsArray[1];
            if (StringUtils.hasLength(dateStr)){
                LocalDate date = LocalDate.parse(dateStr);
                List<LocalTime> freeTimes = bookingService.getFreeSlotsByDate(date);
                if (!userService.bookingLimitNotExceeded(user, date)){
                    String text = SignUpMenu.BOOKING_LIMIT_EXCEEDED.getText();
                    return sendMessageCreator.createSendMessageBody(chatId, text);
                }
                if (freeTimes.size() == 0){
                    String text = String.format(SignUpMenu.NO_FREE_SLOTS.getText(), date.toString());
                    return sendMessageCreator.createSendMessageBody(chatId, text);
                }
                InlineKeyboardMarkup inlineKeyboardMarkup = signUpMenuCreator.createInlineKeyboardMarkupFreeSlots(freeTimes, date);
                String text = "Свободные слоты на " + date;
                return sendMessageCreator.createSendMessageBody(chatId, inlineKeyboardMarkup, text);
            }else {
                log.info("Date is empty. Show bookings is not possible.");
            }
        }if (command.contains(Operation.CANCEL_BOOKING.name())){
            String[] paramsArray = command.split("&");
            final String bookingIdStr = paramsArray[1];
            if (StringUtils.hasLength(bookingIdStr)){
                long bookingId = Long.parseLong(bookingIdStr);
                bookingService.cancelBooking(bookingId, user);
                String text = "Бронирование отменено";
                return sendMessageCreator.createSendMessageBody(chatId, text);
            }else {
                log.info("Booking id is empty. Cancel booking is not possible.");
            }
        }else if (command.contains(Operation.BOOKING.name())){
            String[] paramsArray = command.split("&");
            final String bookingDateTimeIdStr = paramsArray[1];
            if (StringUtils.hasLength(bookingDateTimeIdStr)){
                LocalDateTime bookingDateTime = LocalDateTime.parse(bookingDateTimeIdStr);
                try {
                    bookingService.signUpForFreeSlot(bookingDateTime, user);
                    String text = String.format(SignUpMenu.SUCCESSFUL_SLOT_SELECTION.getText(), bookingDateTime);
                    return sendMessageCreator.createSendMessageBody(chatId, text);
                }catch (Exception e){
                    String text = "Неудачная попытка бронирования.";
                    log.error(e.toString());
                    return sendMessageCreator.createSendMessageBody(chatId, text);
                }
            }
        }
        return null;
    }

    private SendMessage handleInputMessage(Message message){
        String command = message.getText();
        if (command.equals("/start")) {
            return SendMessage
                    .builder()
                    .chatId(message.getChatId())
                    .replyMarkup(menuCreatorService.createInlineKeyboardMarkup(StartMenu.class))
                    .text("Выберите функцию")
                    .build();
        }return SendMessage
                .builder()
                .chatId(message.getChatId())
                .text("Введена некорректная функция. Введите /start, чтобы начать")
                .build();
    }
}
